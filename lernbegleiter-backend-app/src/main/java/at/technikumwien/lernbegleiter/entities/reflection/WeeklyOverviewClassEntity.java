package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_CLASS")
@Entity
public class WeeklyOverviewClassEntity extends BaseEntity<WeeklyOverviewClassEntity> {
  private String name = "";
  private String color = "";
  @OneToMany
  @JoinColumn(name = "WEEKLY_OVERVIEW_CLASS_UUID", nullable = false)
  private Set<WeeklyOverviewClassDayEntity> days = new HashSet<>();
}
