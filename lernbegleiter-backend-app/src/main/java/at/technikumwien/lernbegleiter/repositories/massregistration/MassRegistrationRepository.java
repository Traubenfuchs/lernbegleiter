package at.technikumwien.lernbegleiter.repositories.massregistration;

import at.technikumwien.lernbegleiter.entities.massregistration.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
public interface MassRegistrationRepository extends JpaRepository<MassRegistrationEntity, String> {
  Set<MassRegistrationEntity> findByDeletionTimeBefore(Instant minusSeconds);
}
