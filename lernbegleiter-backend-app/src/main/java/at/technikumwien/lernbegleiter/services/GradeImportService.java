package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import at.technikumwien.lernbegleiter.repositories.*;
import at.technikumwien.lernbegleiter.repositories.modules.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

@AllArgsConstructor
@Service
public class GradeImportService {
  private final GradeRepository gradeRepository;
  private final ClassRepository classRepository;
  private final LearningModuleRepository learningModuleRepository;

  public void importToGradeFrom(String targetUuid, String sourceUuid) {
    GradeEntity targetGrade = gradeRepository.getOne(targetUuid);

    if (targetGrade == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Target grade<" + targetUuid + "> not found!");
    }

    GradeEntity sourceGrade = gradeRepository.getOne(sourceUuid);

    if (sourceGrade == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Source grade<" + targetUuid + "> not found!");
    }

    sourceGrade.getClasses().forEach(classEntity -> {
      ClassEntity newClassEntity = classRepository.save(new ClassEntity()
        .setColor(classEntity.getColor())
        .setGrade(targetGrade)
        .setName(classEntity.getName()));

      classEntity.getModules().stream()
        .map(learningModuleEntity ->
          new LearningModuleEntity()
            .setClazz(newClassEntity)
            .setColor(learningModuleEntity.getColor())
            .setDeadline(learningModuleEntity.getDeadline())
            .setStart(learningModuleEntity.getStart())
            .setDescription(learningModuleEntity.getDescription())
            .setName(learningModuleEntity.getName())
        ).forEach(learningModuleRepository::save);
    });
  }
}
