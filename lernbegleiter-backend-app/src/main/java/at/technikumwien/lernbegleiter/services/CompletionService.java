package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleStudentDto;
import at.technikumwien.lernbegleiter.data.dto.SubModuleStudentDto;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleEntity;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleStudentEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.modules.LearningModuleStudentRepository;
import at.technikumwien.lernbegleiter.repositories.modules.SubModuleStudentRepositories;
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
    private SubModuleStudentRepositories subModuleStudentRepositories;
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
            LearningModuleStudentDto learningModuleStudentDto = new LearningModuleStudentDto()
                    .setUuid(learningModuleStudentEntity.getUuid())
                    .setDueDate(learningModuleStudentEntity.getLearningModule().getDeadline())
                    .setFinishedAt(learningModuleStudentEntity.getFinishedAt())
                    .setName(learningModuleStudentEntity.getLearningModule().getName())
                    .setSubModules(getSubmodules(learningModuleStudentEntity.getLearningModule().getUuid(), userEntity.getSubModuleStudents()));

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

    private Set<SubModuleStudentDto> getSubmodules(String learningModuleUuid, Set<SubModuleStudentEntity> entities) {
        Set<SubModuleStudentDto> result = new HashSet<>();

        for (var entity : entities) {
            if (entity.getSubModule().getParent().getUuid().equals(learningModuleUuid)) {
                result.add(new SubModuleStudentDto()
                        .setUuid(entity.getUuid())
                        .setName(entity.getSubModule().getName())
                        .setDueDate(entity.getSubModule().getDeadline())
                        .setFinishedAt(entity.getFinishedAt()));
            }
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void prepare(@NonNull String userUuid) {
        UserEntity userEntity = userRepository.getOne(userUuid);
        GradeEntity grade = userEntity.getGrade();
        Set<ClassEntity> classes = grade.getClasses();

        Set<LearningModuleStudentEntity> existingLearningModuleStudents = userEntity.getLearningModulesStudents();
        Set<SubModuleStudentEntity> existingSubModuleStudents = userEntity.getSubModuleStudents();

        for (ClassEntity classEntity : classes) {
            for (LearningModuleEntity learningModule : classEntity.getModules()) {
                LearningModuleStudentEntity learningModuleStudent = learningModuleStudentExists(learningModule.getUuid(), existingLearningModuleStudents);
                if (learningModuleStudent == null) {
                    learningModuleStudent = learningModuleStudentRepository.save(
                            new LearningModuleStudentEntity()
                                    .generateUuid()
                                    .setLearningModule(learningModule)
                                    .setStudent(userEntity)
                    );
                }

                for (SubModuleEntity subModule : learningModule.getSubModules()) {
                    SubModuleStudentEntity subModuleStudentEntity = getSubModuleStudent(subModule.getUuid(), existingSubModuleStudents);
                    if (subModuleStudentEntity != null) {
                        continue;
                    }
                    subModuleStudentEntity = subModuleStudentRepositories.save(
                            new SubModuleStudentEntity()
                                    .generateUuid()
                                    .setSubModule(subModule)
                                    .setStudent(userEntity)
                    );
                }
            }
        }
    }

    private SubModuleStudentEntity getSubModuleStudent(String subModuleUuid, Set<SubModuleStudentEntity> subModuleStudentEntities) {
        for (SubModuleStudentEntity subModuleStudentEntity : subModuleStudentEntities) {
            if (subModuleStudentEntity.getSubModule().getUuid().equals(subModuleUuid)) {
                return subModuleStudentEntity;
            }
        }
        return null;
    }

    private LearningModuleStudentEntity learningModuleStudentExists(String learningModuleUuid, Set<LearningModuleStudentEntity> learningModuleStudentEntities) {
        for (LearningModuleStudentEntity existingLearningModuleStudent : learningModuleStudentEntities) {
            if (existingLearningModuleStudent.getLearningModule().getUuid().equals(learningModuleUuid)) {
                return existingLearningModuleStudent;
            }
        }
        return null;
    }

    public void markSubModuleStudentAsComplete(@NonNull String subModuleStudentUuid) {
        subModuleStudentRepositories.getOne(subModuleStudentUuid).setFinishedAt(Instant.now());
    }

    public void markLearningModuleStudentAsComplete(@NonNull String learningModuleStudentUuid) {
        learningModuleStudentRepository.getOne(learningModuleStudentUuid).setFinishedAt(Instant.now());
    }
}
