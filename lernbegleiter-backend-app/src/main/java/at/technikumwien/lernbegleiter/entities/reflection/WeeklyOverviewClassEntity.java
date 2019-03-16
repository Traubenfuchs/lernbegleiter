package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_CLASS")
@Entity
public class WeeklyOverviewClassEntity extends BaseEntity<WeeklyOverviewClassEntity> {
  private String name = "";
  private String color = "";

  @ManyToOne
  @JoinColumn(name = "WEEKLY_OVERVIEW_UUID", nullable = false)
  private WeeklyOverviewEntity weeklyOverview;

  @OneToMany(mappedBy = "weeklyOverviewClass")
  private Set<WeeklyOverviewClassDayEntity> days = new HashSet<>();
}
