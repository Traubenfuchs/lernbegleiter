package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class ClassConverter extends DtoEntityConverter<ClassEntity, ClassDto> {
  @Autowired
  private LearningModuleConverter learningModuleConverter;
  @Autowired
  private GradeRepository gradeRepository;

  @Override
  public void applyToDto(ClassEntity classEntity, ClassDto classDto) {
    GradeEntity ge = classEntity.getGrade();
    String gradeName = ge == null ? null : ge.getName();

    classDto
      .setUuid(classEntity.getUuid())
      .setName(classEntity.getName())
      .setGradeName(gradeName)
      .setColor(classEntity.getColor())
      .setLearningModules(learningModuleConverter.toDtoSet(classEntity.getModules()))
    ;
  }

  @Override
  public void applyToEntity(ClassDto classDto, ClassEntity classEntity) {
    classEntity
      .setUuid(classDto.getUuid())
      .setName(classDto.getName())
      .setColor(classDto.getColor())
      .setGrade(gradeRepository.findByName(classDto.getGradeName()))
    ;
  }
}
