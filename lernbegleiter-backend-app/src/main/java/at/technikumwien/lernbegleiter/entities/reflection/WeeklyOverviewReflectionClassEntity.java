package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_REFLECTION_CLASS")
@Entity
public class WeeklyOverviewReflectionClassEntity extends BaseEntity<WeeklyOverviewReflectionClassEntity> {
  private String color = "";
  private String progress = "";
  private String improvements ="";
}
