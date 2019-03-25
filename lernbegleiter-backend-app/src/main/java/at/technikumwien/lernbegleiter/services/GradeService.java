package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.GradeDto;
import at.technikumwien.lernbegleiter.data.dto.converter.GradeConverter;
import at.technikumwien.lernbegleiter.data.requests.CreateGradeRequest;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Validated
@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private GradeConverter gradeConverter;

    public GradeDto getOne(String uuid) {
        return gradeConverter.toDTO(gradeRepository.getOne(uuid));
    }

    public Set<GradeDto> getAll() {
        return gradeConverter.toDtoSet(gradeRepository.findAll());
    }

    public String create(CreateGradeRequest createGradeRequest) {
        return gradeRepository.save(
                new GradeEntity()
                        .setName(createGradeRequest.getName())
                        .generateUuid())
                .getUuid();
    }

    public void delete(String uuid) {
        gradeRepository.deleteById(uuid);
    }

    public void deleteStudentFromGrade(String studentUuid, String gradeUuid) {
        GradeEntity ge = gradeRepository.getOne(gradeUuid);

        ge.setStudents(
                ge.getStudents().stream()
                        .filter(student ->{ student.setGrade(null); return !student.getUuid().equals(studentUuid);})
                        .collect(Collectors.toSet()));
    }
}
