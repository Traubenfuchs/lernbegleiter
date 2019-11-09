package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.auth.*;
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

import static javax.persistence.FetchType.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"FK_STUDENT_UUID", "CALENDAR_WEEK", "YEAR"})
  }, indexes = {
  @Index(name = "I_WEEKLY_OVERVIEW_FK_STUDENT_UUID", columnList = "FK_STUDENT_UUID")
})
@Entity
public class WeeklyOverviewEntity extends BaseEntity<WeeklyOverviewEntity> {
  @Column(name = "CALENDAR_WEEK", nullable = false)
  private Integer calendarWeek;
  @Lob
  @Column(name = "MY_WEEKLY_GOALS", length = 10240)
  private String myWeeklyGoals = "";
  @Lob
  @Column(name = "FURTHER_STEPS", length = 10240)
  private String furtherSteps = "";

  @Column(name = "YEAR", nullable = false)
  private Short year;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyOverview", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyOverview", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewReflectionClassEntity> weeklyOVerviewReflectionClasses = new HashSet<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  public WeeklyOverviewClassEntity getWeeklyOverviewClassByUuid(String uuid) {
    return weeklyOverviewClasses.stream().filter(woc -> woc.getUuid().equals(uuid)).findFirst().orElseGet(null);
  }

  public WeeklyOverviewReflectionClassEntity getWeeklyOverviewReflectionClassByUuid(String uuid) {
    return weeklyOVerviewReflectionClasses.stream().filter(rc -> rc.getUuid().equals(uuid)).findFirst().orElseGet(null);
  }
}
