package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.modules.*;
import lombok.*;
import lombok.experimental.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Transactional
@Validated
@Service
public class CompletionService {
  @Lazy
  @Autowired
  private CompletionService completionService;
  @Autowired
  private LearningModuleStudentRepository learningModuleStudentRepository;
  @Autowired
  private UserRepository userRepository;

  @Accessors(chain = true)
  @Data
  public static class ClassCompletion {
    private String className;
    private LocalDate deadline;
    private String color;
    private Set<LearningModuleStudentDto> learningModulesStudent = new HashSet<>();
  }

  public Set<ClassCompletion> getAll(@NonNull String userUuid) {
    completionService.prepare(userUuid);

    Set<ClassCompletion> result = new HashSet<>();
    Map<String, ClassCompletion> uuidToClassCompletions = new HashMap<>();

    UserEntity userEntity = userRepository.getOne(userUuid);

    for (LearningModuleStudentEntity learningModuleStudentEntity : userEntity.getLearningModulesStudents()) {
      Instant lateComparisonInstant = learningModuleStudentEntity.getFinishedAt();
      if (lateComparisonInstant == null) {
        lateComparisonInstant = Instant.now();
      }

      LearningModuleStudentDto learningModuleStudentDto = new LearningModuleStudentDto()
        .setUuid(learningModuleStudentEntity.getUuid())
        .setColor(learningModuleStudentEntity.getLearningModule().getColor())
        .setDueDate(learningModuleStudentEntity.getLearningModule().getDeadline())
        .setFinishedAt(learningModuleStudentEntity.getFinishedAt())
        .setName(learningModuleStudentEntity.getLearningModule().getName())
        .setLate(lateComparisonInstant.isAfter(learningModuleStudentEntity.getLearningModule().getDeadline().atStartOfDay().plus(1, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)));

      uuidToClassCompletions
        .computeIfAbsent(learningModuleStudentEntity.getLearningModule().getClazz().getUuid(), k -> {
          ClassCompletion r = new ClassCompletion()
            .setColor(learningModuleStudentEntity.getLearningModule().getClazz().getColor())
            .setClassName(learningModuleStudentEntity.getLearningModule().getClazz().getName());
          if (r.getDeadline() == null || r.getDeadline().isBefore(learningModuleStudentDto.getDueDate())) {
            r.setDeadline(learningModuleStudentDto.getDueDate());
          }

          result.add(r);
          return r;
        })
        .getLearningModulesStudent()
        .add(learningModuleStudentDto);
    }

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void prepare(@NonNull String userUuid) {
    UserEntity userEntity = userRepository.getOne(userUuid);
    GradeEntity grade = userEntity.getGrade();
    Set<ClassEntity> classes = grade.getClasses();

    Set<LearningModuleStudentEntity> existingLearningModuleStudents = userEntity.getLearningModulesStudents();

    classes.stream().flatMap(classEntity -> classEntity.getModules().stream()).forEach(learningModule -> {
      LearningModuleStudentEntity learningModuleStudent = learningModuleStudentExists(learningModule.getUuid(), existingLearningModuleStudents);
      if (learningModuleStudent == null) {
        learningModuleStudentRepository.save(
          new LearningModuleStudentEntity()
            .generateUuid()
            .setLearningModule(learningModule)
            .setStudent(userEntity)
        );
      }
    });
  }

  private LearningModuleStudentEntity learningModuleStudentExists(String learningModuleUuid, Set<LearningModuleStudentEntity> learningModuleStudentEntities) {
    return learningModuleStudentEntities.stream().filter(existingLearningModuleStudent -> existingLearningModuleStudent.getLearningModule().getUuid().equals(learningModuleUuid)).findFirst().orElse(null);
  }

  public void markLearningModuleStudentAsComplete(@NonNull String learningModuleStudentUuid) {
    learningModuleStudentRepository.getOne(learningModuleStudentUuid).setFinishedAt(Instant.now());
  }

  public void markLearningModuleStudentAsIncomplete(@NonNull String learningModuleStudentUuid) {
    learningModuleStudentRepository.getOne(learningModuleStudentUuid).setFinishedAt(null);
  }
}
