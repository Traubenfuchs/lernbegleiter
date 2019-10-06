package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleStudentDto;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleStudentEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.modules.LearningModuleStudentRepository;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @Data
    public static class ClassCompletion {
        private String className;
        private LocalDate deadline;
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
                    .setDueDate(learningModuleStudentEntity.getLearningModule().getDeadline())
                    .setFinishedAt(learningModuleStudentEntity.getFinishedAt())
                    .setName(learningModuleStudentEntity.getLearningModule().getName())
                    .setLate(lateComparisonInstant.isAfter(learningModuleStudentEntity.getLearningModule().getDeadline().atStartOfDay().plus(1, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)));

            uuidToClassCompletions
                    .computeIfAbsent(learningModuleStudentEntity.getLearningModule().getClazz().getUuid(), k -> {
                        ClassCompletion r = new ClassCompletion();
                        r.setClassName(learningModuleStudentEntity.getLearningModule().getClazz().getName());
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

        for (ClassEntity classEntity : classes) {
            for (LearningModuleEntity learningModule : classEntity.getModules()) {
                LearningModuleStudentEntity learningModuleStudent = learningModuleStudentExists(learningModule.getUuid(), existingLearningModuleStudents);
                if (learningModuleStudent == null) {
                    learningModuleStudentRepository.save(
                            new LearningModuleStudentEntity()
                                    .generateUuid()
                                    .setLearningModule(learningModule)
                                    .setStudent(userEntity)
                    );
                }
            }
        }
    }

    private LearningModuleStudentEntity learningModuleStudentExists(String learningModuleUuid, Set<LearningModuleStudentEntity> learningModuleStudentEntities) {
        for (LearningModuleStudentEntity existingLearningModuleStudent : learningModuleStudentEntities) {
            if (existingLearningModuleStudent.getLearningModule().getUuid().equals(learningModuleUuid)) {
                return existingLearningModuleStudent;
            }
        }
        return null;
    }

    public void markLearningModuleStudentAsComplete(@NonNull String learningModuleStudentUuid) {
        learningModuleStudentRepository.getOne(learningModuleStudentUuid).setFinishedAt(Instant.now());
    }

    public void markLearningModuleStudentAsIncomplete(@NonNull String learningModuleStudentUuid) {
        learningModuleStudentRepository.getOne(learningModuleStudentUuid).setFinishedAt(null);
    }
}
