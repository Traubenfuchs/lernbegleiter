package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW_REFLECTION_CLASS", indexes = {
  @Index(name = "I_WEEKLY_OVERVIEW_REFLECTION_CLASS_FK_WEEKLY_OVERVIEW_UUID", columnList = "FK_WEEKLY_OVERVIEW_UUID"),
  @Index(name = "I_WEEKLY_OVERVIEW_REFLECTION_CLASS_FK_CLASS_UUID", columnList = "FK_CLASS_UUID")
})
@Entity
public class WeeklyOverviewReflectionClassEntity extends BaseEntity<WeeklyOverviewReflectionClassEntity> {
  @Lob
  @Column(name = "PROGRESS", length = 10240)
  private String progress;
  @Lob
  @Column(name = "IMPROVEMENTS", length = 10240)
  private String improvements;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_WEEKLY_OVERVIEW_UUID", nullable = false)
  private WeeklyOverviewEntity weeklyOverview;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
  private ClassEntity clazz;

  @Column(name = "FK_CLASS_UUID", insertable = false, updatable = false)
  private String fkClassUuid;
}
