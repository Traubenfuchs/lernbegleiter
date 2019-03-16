package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW")
@Entity
public class WeeklyOverviewEntity extends BaseEntity<WeeklyOverviewEntity> {
  private String kw = "";
  @Column(name="MY_WEEKLY_GOALS", length = 10240)
  private String myWeeklyGoals = "";
  @Column(name="FIRST_DAY_OF_WEEK")
  private String firstDayOfWeek = "";
  @Column(name="LAST_DAY_OF_WEEK")
  private String lastDayOfWeek = "";
  @Column(name="FURTHER_STEPS", length = 10240)
  private String furtherSteps = "";

  @OneToMany(mappedBy = "weeklyOverview")
  private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

  @OneToMany(mappedBy = "weeklyOverview")
  private Set<WeeklyOverviewReflectionClassEntity> reflexionClasses = new HashSet<>();
}
