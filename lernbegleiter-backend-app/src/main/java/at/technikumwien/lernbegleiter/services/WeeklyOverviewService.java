package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.converter.reflexion.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.reflection.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import javax.validation.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

@Transactional
@Validated
@Service
public class WeeklyOverviewService {
  @Autowired
  private WeeklyOverviewRepository weeklyOverviewRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private WeeklyOverviewConverter weeklyOverviewConverter;
  @Autowired
  private WeeklyOverviewReflectionClassRepository weeklyOverviewReflectionClassRepository;
  @Autowired
  private AuthHelper authHelper;
  @Autowired
  private WeeklyOverviewClassRepository weeklyOverviewClassRepository;
  @Autowired
  private WeeklyOverviewClassDayRepository weeklyOverviewClassDayRepository;

  public WeeklyOverviewDto adaptStudentsWeeklyOverview(
    @NonNull String studentUuid,
    @NonNull Integer calendarWeek,
    @NonNull Short year
  ) {
    WeeklyOverviewEntity woe = adaptStudentsWeeklyOverviewEntity(studentUuid, calendarWeek, year);
    authHelper.isAdminOrTeacherOrCurrentUserUuidOrThrow(woe.getStudent().getUuid());
    return weeklyOverviewConverter.toDTO(woe);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public WeeklyOverviewEntity adaptStudentsWeeklyOverviewEntity(
    @NonNull String studentUuid,
    @NonNull Integer calendarWeek,
    @NonNull Short year
  ) {
    LocalDate sundayOfGivenWeek = LocalDate.ofYearDay(year, 50)
      .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
      .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

    LocalDate lastSunday = sundayOfGivenWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

    WeeklyOverviewEntity woe = findByStudentUuidAndAndCalendarWeek(studentUuid, calendarWeek, year);

    GradeEntity grade = woe.getStudent().getGrade();
    Set<ClassEntity> classes = grade.getClasses().stream()
      .filter(clazz -> clazz.getModules().stream().anyMatch(module ->
        lastSunday.isBefore(module.getDeadline())
          && sundayOfGivenWeek.isAfter(module.getStart())
      ))
      .collect(Collectors.toSet());

    prepareReflectionClasses(woe, classes);


    prepareWeeklyOverviewClasses(woe, classes);

    return weeklyOverviewRepository.save(woe);
  }

  private void prepareReflectionClasses(
    WeeklyOverviewEntity woe,
    Set<ClassEntity> currentlyExistingClasses
  ) {
    Set<WeeklyOverviewReflectionClassEntity> reflectionClasses = woe.getReflexionClasses();
    Set<String> classNames = currentlyExistingClasses.stream().map(ClassEntity::getName).collect(Collectors.toSet());
    reflectionClasses.removeIf(wrce -> !classNames.contains(wrce.getClazz().getName()));

    Set<String> existingClassNames = reflectionClasses.stream().map(x -> x.getClazz().getName()).collect(Collectors.toSet());

    currentlyExistingClasses.stream()
      .filter(s -> !existingClassNames.contains(s.getName()))
      .forEach(classEntity -> reflectionClasses.add(
        new WeeklyOverviewReflectionClassEntity()
          .setClazz(classEntity)
          .setWeeklyOverview(woe))
      );
  }

  private void prepareWeeklyOverviewClasses(
    WeeklyOverviewEntity woe,
    Set<ClassEntity> currentlyExistingClasses
  ) {
    Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = woe.getWeeklyOverviewClasses();

    Set<String> classNames = currentlyExistingClasses.stream().map(ClassEntity::getName).collect(Collectors.toSet());
    weeklyOverviewClasses.removeIf(woce -> !classNames.contains(woce.getClazz().getName()));

    Set<String> existingClassNames = weeklyOverviewClasses.stream().map(x -> x.getClazz().getName()).collect(Collectors.toSet());

    currentlyExistingClasses.stream()
      .filter(s -> !existingClassNames.contains(s.getName()))
      .forEach(classEntity -> {

        WeeklyOverviewClassEntity woce = new WeeklyOverviewClassEntity();

        woce.setDays(
          IntStream
            .rangeClosed(0, 4)
            .mapToObj(i -> new WeeklyOverviewClassDayEntity()
              .setWeeklyOverviewClass(woce)
              .setDayOfWeek(i)).collect(Collectors.toSet()))
          .setClazz(classEntity)
          .setWeeklyOverview(woe);

        weeklyOverviewClasses.add(woce);
      });
  }

  private WeeklyOverviewEntity findByStudentUuidAndAndCalendarWeek(
    String studentUuid,
    Integer calendarWeek,
    Short year
  ) {
    UserEntity userEntity = userRepository.getOne(studentUuid);

    WeeklyOverviewEntity weeklyOverviewEntity = weeklyOverviewRepository.findByStudentUuidAndAndCalendarWeek(studentUuid, calendarWeek);
    if (weeklyOverviewEntity == null) {
      weeklyOverviewEntity = new WeeklyOverviewEntity()
        .setStudent(userEntity)
        .setCalendarWeek(calendarWeek)
        .setYear(year)
        .generateUuid();
    }
    return weeklyOverviewEntity;
  }

  public void patch(
    @NonNull String studentUuid,
    @NonNull @Valid WeeklyOverviewDto weeklyOverviewDto
  ) {
    WeeklyOverviewEntity woe = adaptStudentsWeeklyOverviewEntity(
      studentUuid,
      weeklyOverviewDto.getCalendarWeek(),
      weeklyOverviewDto.getYear());

    authHelper.isAdminOrTeacherOrCurrentUserUuidOrThrow(woe.getStudent().getUuid());

    woe.setFurtherSteps(weeklyOverviewDto.getFurtherSteps());
    woe.setMyWeeklyGoals(weeklyOverviewDto.getMyWeeklyGoals());

    for (WeeklyOverviewClassDto weeklyOverviewClassDto : weeklyOverviewDto.getWeeklyOverviewClasses()) {
      WeeklyOverviewClassEntity weeklyOverviewClassEntity = woe.getwWeeklyOverviewClassByName(weeklyOverviewClassDto.getName());
      if (weeklyOverviewClassEntity == null) {
        continue;
      }
      weeklyOverviewClassEntity.setColor(weeklyOverviewClassDto.getColor());
      ArrayList<WeeklyOverviewClassDayEntity> orderedDays = weeklyOverviewClassEntity.getDaysOrdered();

      int i = 0;
      for (WeeklyOverviewClassDayDto weeklyOverviewClassDayDto : weeklyOverviewClassDto.getDays()) {
        WeeklyOverviewClassDayEntity de = orderedDays.get(i);
        if (de == null) {
          continue;
        }
        de
          .setStudentComment(weeklyOverviewClassDayDto.getStudentComment())
          .setTeacherComment(weeklyOverviewClassDayDto.getTeacherComment());
        i++;
      }
    }

    for (WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto : weeklyOverviewDto.getReflexionClasses()) {
      WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity = woe.getWeeklyOverviewReflectionClassByName(weeklyOverviewReflectionClassDto.getName());
      if (weeklyOverviewReflectionClassEntity == null) {
        continue;
      }

      weeklyOverviewReflectionClassEntity.setColor(weeklyOverviewReflectionClassDto.getColor());
      weeklyOverviewReflectionClassEntity.setImprovements(weeklyOverviewReflectionClassDto.getImprovements());
      weeklyOverviewReflectionClassEntity.setProgress(weeklyOverviewReflectionClassDto.getProgress());
    }
  }
}
