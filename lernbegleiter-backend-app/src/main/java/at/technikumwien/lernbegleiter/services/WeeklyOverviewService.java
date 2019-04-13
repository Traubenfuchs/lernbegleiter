package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.converter.reflexion.WeeklyOverviewConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassDayEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewReflectionClassEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewClassDayRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewClassRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewReflectionRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Validated
@Service
public class WeeklyOverviewService {
    @Autowired
    private WeeklyOverviewClassDayRepository weeklyOverviewClassDayRepository;
    @Autowired
    private WeeklyOverviewClassRepository weeklyOverviewClassRepository;
    @Autowired
    private WeeklyOverviewRepository weeklyOverviewRepository;
    @Autowired
    private WeeklyOverviewReflectionRepository weeklyOverviewReflectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeeklyOverviewConverter weeklyOverviewConverter;

    public WeeklyOverviewDto adaptStudentsWeeklyOverview(
            @NonNull String studentUuid,
            @NonNull Integer calendarWeek
    ) {
        WeeklyOverviewEntity woe = findByStudentUuidAndAndCalendarWeek(studentUuid, calendarWeek);

        GradeEntity grade = woe.getStudent().getGrade();
        Set<ClassEntity> classes = grade.getClasses();

        Set<WeeklyOverviewReflectionClassEntity> reflectionClasses = woe.getReflexionClasses();
        prepareReflectionClasses(woe, reflectionClasses, classes);

        Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = woe.getWeeklyOverviewClasses();
        prepareWeeklyOverviewClasses(woe, weeklyOverviewClasses, classes);

        weeklyOverviewRepository.save(woe);

        return weeklyOverviewConverter.toDTO(woe);
    }

    private void prepareReflectionClasses(
            WeeklyOverviewEntity woe,
            Set<WeeklyOverviewReflectionClassEntity> reflectionClasses,
            Set<ClassEntity> classes
    ) {
        Set<String> classNames = classes.stream().map(ClassEntity::getName).collect(Collectors.toSet());
        reflectionClasses.removeIf(wrce -> !classNames.contains(wrce.getClazz().getName()));

        Set<String> existingClassNames = reflectionClasses.stream().map(x -> x.getClazz().getName()).collect(Collectors.toSet());

        classes.stream()
                .filter(s -> !existingClassNames.contains(s.getName()))
                .forEach(classEntity -> reflectionClasses.add(
                        new WeeklyOverviewReflectionClassEntity()
                                .generateUuid()
                                .setClazz(classEntity)
                                .setWeeklyOverview(woe)
                ));
    }

    private void prepareWeeklyOverviewClasses(
            WeeklyOverviewEntity woe,
            Set<WeeklyOverviewClassEntity> weeklyOverviewClasses,
            Set<ClassEntity> classes
    ) {
        Set<String> classNames = classes.stream().map(ClassEntity::getName).collect(Collectors.toSet());
        weeklyOverviewClasses.removeIf(woce -> !classNames.contains(woce.getClazz().getName()));

        Set<String> existingClassNames = weeklyOverviewClasses.stream().map(x -> x.getClazz().getName()).collect(Collectors.toSet());

        classes.stream()
                .filter(s -> !existingClassNames.contains(s.getName()))
                .forEach(classEntity -> {

                    WeeklyOverviewClassEntity woce = new WeeklyOverviewClassEntity()
                            .generateUuid()
                            .setClazz(classEntity)
                            .setWeeklyOverview(woe)
                            ;

                    for(int i = 0; i < 5; i++) {
                        woce.getDays().add(new WeeklyOverviewClassDayEntity()
                                .generateUuid()
                                .setWeeklyOverviewClass(woce)
                                .setDayOfWeek(i)
                        );
                    }

                    weeklyOverviewClasses.add(woce);
                });
    }

    private WeeklyOverviewEntity findByStudentUuidAndAndCalendarWeek(
            String studentUuid,
            Integer calendarWeek
    ) {
        UserEntity userEntity = userRepository.getOne(studentUuid);

        WeeklyOverviewEntity weeklyOverviewEntity = weeklyOverviewRepository.findByStudentUuidAndAndCalendarWeek(studentUuid, calendarWeek);
        if(weeklyOverviewEntity == null) {
            weeklyOverviewEntity = new WeeklyOverviewEntity()
                    .setStudent(userEntity)
                    .setCalendarWeek(calendarWeek)
                    .generateUuid();
        }
        return weeklyOverviewEntity;
       // return weeklyOverviewRepository.save(weeklyOverviewEntity);
    }

    public void patch(
            @NonNull String studentUuid,
            @NonNull @Valid WeeklyOverviewDto weeklyOverviewDto
    ) {

    }
}
