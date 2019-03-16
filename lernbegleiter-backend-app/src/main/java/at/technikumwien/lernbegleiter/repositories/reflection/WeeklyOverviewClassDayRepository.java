package at.technikumwien.lernbegleiter.repositories.reflection;

import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyOverviewClassDayRepository extends JpaRepository<WeeklyOverviewClassDayEntity,String> {

}
