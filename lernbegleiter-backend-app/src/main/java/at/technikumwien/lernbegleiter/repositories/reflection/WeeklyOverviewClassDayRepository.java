package at.technikumwien.lernbegleiter.repositories.reflection;

import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface WeeklyOverviewClassDayRepository extends JpaRepository<WeeklyOverviewClassDayEntity, String> {

}
