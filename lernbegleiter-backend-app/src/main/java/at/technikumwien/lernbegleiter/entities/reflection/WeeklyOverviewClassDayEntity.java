package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_CLASS_DAY")
@Entity
public class WeeklyOverviewClassDayEntity extends BaseEntity<WeeklyOverviewClassDayEntity> {
  @Column(name="TEACHER_COMMENT")
  private String teacherComment = "";
  @Column(name="STUDENT_COMMENT")
  private String studentComment = "";
}
