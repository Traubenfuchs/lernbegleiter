package at.technikumwien.lernbegleiter.repositories.reflection;

import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyOverviewRepository extends JpaRepository<WeeklyOverviewEntity, String> {
  WeeklyOverviewEntity findByStudentUuidAndAndCalendarWeek(String uuid, Integer calendarWeek);
}
