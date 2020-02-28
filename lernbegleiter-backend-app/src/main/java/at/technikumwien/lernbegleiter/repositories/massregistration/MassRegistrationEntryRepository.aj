package at.technikumwien.lernbegleiter.repositories.massregistration;

import at.technikumwien.lernbegleiter.entities.massregistration.MassRegistrationEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MassRegistrationEntryRepository extends JpaRepository<MassRegistrationEntryEntity, String> {
}
