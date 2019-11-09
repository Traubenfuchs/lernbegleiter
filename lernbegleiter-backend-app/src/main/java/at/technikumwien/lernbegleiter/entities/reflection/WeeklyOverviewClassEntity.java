package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW_CLASS", indexes = {
  @Index(name = "I_WEEKLY_OVERVIEW_CLASS_FK_WEEKLY_OVERVIEW_UUID", columnList = "FK_WEEKLY_OVERVIEW_UUID"),
  @Index(name = "I_WEEKLY_OVERVIEW_CLASS_FK_CLASS_UUID", columnList = "FK_CLASS_UUID")
})
@Entity
public class WeeklyOverviewClassEntity extends BaseEntity<WeeklyOverviewClassEntity> {
  @Column(name = "COLOR")
  private String color;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_WEEKLY_OVERVIEW_UUID", nullable = false)
  private WeeklyOverviewEntity weeklyOverview;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
  private ClassEntity clazz;

  @Column(name = "FK_CLASS_UUID", updatable = false, insertable = false)
  private String fkClassUuid;

  @OneToMany(mappedBy = "weeklyOverviewClass", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewClassDayEntity> days = new HashSet<>();

  public ArrayList<WeeklyOverviewClassDayEntity> getDaysOrdered() {
    ArrayList<WeeklyOverviewClassDayEntity> result = new ArrayList<>(days);
    result.sort(Comparator.comparing(WeeklyOverviewClassDayEntity::getDayOfWeek));
    return result;
  }
}
