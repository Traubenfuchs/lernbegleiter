package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.ClassDto;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        .setLearningModules(learningModuleConverter.toDtoSet(classEntity.getModules()))
    ;
  }

  @Override
  public void applyToEntity(ClassDto classDto, ClassEntity classEntity) {
    classEntity
        .setUuid(classDto.getUuid())
        .setName(classDto.getName())
        .setGrade(gradeRepository.findByName(classDto.getGradeName()))
    ;
  }
}
