package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.converter.reflexion.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.reflection.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.server.*;

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
    Set<WeeklyOverviewReflectionClassEntity> reflectionClasses = woe.getWeeklyOVerviewReflectionClasses();
    Set<String> uuidsOfExistingClasses = currentlyExistingClasses.stream().map(ClassEntity::getUuid).collect(Collectors.toSet());
    reflectionClasses.removeIf(wrce -> !uuidsOfExistingClasses.contains(wrce.getFkClassUuid()));
    Set<String> currentlyMappedClassUuids = reflectionClasses.stream().map(x -> x.getFkClassUuid()).collect(Collectors.toSet());

    reflectionClasses.addAll(currentlyExistingClasses.stream()
      .filter(existingClass -> !currentlyMappedClassUuids.contains(existingClass.getUuid()))
      .map(classEntity ->
        new WeeklyOverviewReflectionClassEntity()
          .setClazz(classEntity)
          .setWeeklyOverview(woe)
      ).collect(Collectors.toList()));
  }

  private void prepareWeeklyOverviewClasses(
    WeeklyOverviewEntity woe,
    Set<ClassEntity> currentlyExistingClasses
  ) {
    Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = woe.getWeeklyOverviewClasses();
    Set<String> uuidsOfExistingClasses = currentlyExistingClasses.stream().map(ClassEntity::getUuid).collect(Collectors.toSet());
    weeklyOverviewClasses.removeIf(woce -> !uuidsOfExistingClasses.contains(woce.getFkClassUuid()));
    Set<String> currentlyMappedClassUuids = weeklyOverviewClasses.stream().map(x -> x.getFkClassUuid()).collect(Collectors.toSet());

    weeklyOverviewClasses.addAll(currentlyExistingClasses.stream()
      .filter(existingClass -> !currentlyMappedClassUuids.contains(existingClass.getUuid()))
      .map(classEntity -> {
        WeeklyOverviewClassEntity woce = new WeeklyOverviewClassEntity();
        woce.setDays(
          IntStream
            .rangeClosed(0, 4)
            .mapToObj(i -> new WeeklyOverviewClassDayEntity()
              .setWeeklyOverviewClass(woce)
              .setDayOfWeek(i)).collect(Collectors.toSet()))
          .setClazz(classEntity)
          .setWeeklyOverview(woe);
        return woce;
      }).collect(Collectors.toList()));
  }

  private WeeklyOverviewEntity findByStudentUuidAndAndCalendarWeek(
    String studentUuid,
    Integer calendarWeek,
    Short year
  ) {
    if (calendarWeek > 54 || calendarWeek < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given calendar week <" + calendarWeek + "> is out of range.");
    }
    if (year < 2019 || year > 2030) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given year<" + year + "> is out of range.");
    }

    WeeklyOverviewEntity weeklyOverviewEntity = weeklyOverviewRepository.findByStudentUuidAndAndCalendarWeek(studentUuid, calendarWeek);
    if (weeklyOverviewEntity != null) {
      return weeklyOverviewEntity;
    }

    return new WeeklyOverviewEntity()
      .setStudent(userRepository.getOne(studentUuid))
      .setCalendarWeek(calendarWeek)
      .setYear(year)
      .generateUuid();
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

    woe
      .setFurtherSteps(weeklyOverviewDto.getFurtherSteps())
      .setMyWeeklyGoals(weeklyOverviewDto.getMyWeeklyGoals());

    for (WeeklyOverviewClassDto weeklyOverviewClassDto : weeklyOverviewDto.getWeeklyOverviewClasses()) {
      ArrayList<WeeklyOverviewClassDayEntity> orderedDays = woe
        .getWeeklyOverviewClassByUuid(weeklyOverviewClassDto.getUuid())
        .setColor(weeklyOverviewClassDto.getColor())
        .getDaysOrdered();

      int i = 0;
      for (WeeklyOverviewClassDayDto weeklyOverviewClassDayDto : weeklyOverviewClassDto.getDays()) {
        WeeklyOverviewClassDayEntity weeklyOverviewClassDayEntity = orderedDays.get(i);

        weeklyOverviewClassDayEntity
          .setStudentComment(weeklyOverviewClassDayDto.getStudentComment())
          .setTeacherComment(weeklyOverviewClassDayDto.getTeacherComment());
        i++;
      }
    }

    for (WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto : weeklyOverviewDto.getReflexionClasses()) {
      woe
        .getWeeklyOverviewReflectionClassByUuid(weeklyOverviewReflectionClassDto.getUuid())
        .setColor(weeklyOverviewReflectionClassDto.getColor())
        .setImprovements(weeklyOverviewReflectionClassDto.getImprovements())
        .setProgress(weeklyOverviewReflectionClassDto.getProgress());
    }
  }
}