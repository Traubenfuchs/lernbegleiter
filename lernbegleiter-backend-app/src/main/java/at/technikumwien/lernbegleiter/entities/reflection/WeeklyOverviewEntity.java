package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

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

  @OneToMany(mappedBy = "weeklyOverview", fetch = EAGER, cascade = CascadeType.ALL)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

  @OneToMany(mappedBy = "weeklyOverview", fetch = EAGER, cascade = CascadeType.ALL)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewReflectionClassEntity> reflexionClasses = new HashSet<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  public WeeklyOverviewClassEntity getwWeeklyOverviewClassByName(String name) {
    for (WeeklyOverviewClassEntity weeklyOverviewClassEntity : weeklyOverviewClasses) {
      if (weeklyOverviewClassEntity.getClazz().getName().equals(name)) {
        return weeklyOverviewClassEntity;
      }
    }
    return null;
  }

  public WeeklyOverviewReflectionClassEntity getWeeklyOverviewReflectionClassByName(String name) {
    for (WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity : reflexionClasses) {
      if (weeklyOverviewReflectionClassEntity.getClazz().getName().equals(name)) {
        return weeklyOverviewReflectionClassEntity;
      }
    }

    return null;
  }
}
