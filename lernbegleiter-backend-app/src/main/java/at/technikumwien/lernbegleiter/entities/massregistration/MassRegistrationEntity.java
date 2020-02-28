package at.technikumwien.lernbegleiter.entities.massregistration;

import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.*;
import java.util.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "MASS_REGISTRATION", indexes = {})
public class MassRegistrationEntity extends BaseEntityCreationUpdateDate<MassRegistrationEntity> {
  @Column(name = "NAME")
  private String name;
  @Column(name = "NOTES")
  private String notes;
  @Column(name = "DELETION_TIME")
  private Instant deletionTime;
  @Column(name = "AMOUNT")
  private Integer amount;
  @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "owner", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<MassRegistrationEntryEntity> children = new HashSet<>();

  public MassRegistrationEntity addChild(MassRegistrationEntryEntity child) {
    child.setOwner(this);
    children.add(child);
    return this;
  }
}