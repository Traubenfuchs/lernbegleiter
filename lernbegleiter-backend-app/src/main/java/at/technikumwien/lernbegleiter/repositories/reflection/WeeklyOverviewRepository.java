package at.technikumwien.lernbegleiter.repositories.reflection;

import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface WeeklyOverviewRepository extends JpaRepository<WeeklyOverviewEntity, String> {
  WeeklyOverviewEntity findByStudentUuidAndAndCalendarWeek(String uuid, Integer calendarWeek);
}
