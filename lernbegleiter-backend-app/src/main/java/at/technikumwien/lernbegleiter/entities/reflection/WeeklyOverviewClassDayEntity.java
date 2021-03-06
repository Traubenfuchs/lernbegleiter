package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW_CLASS_DAY", indexes = {
  @Index(name = "I_WEEKLY_OVERVIEW_CLASS_DAY_FK_WEEKLY_OVERVIEW_CLASS_UUID", columnList = "FK_WEEKLY_OVERVIEW_CLASS_UUID")
})
@Entity
public class WeeklyOverviewClassDayEntity extends BaseEntity<WeeklyOverviewClassDayEntity> {
  @Lob
  @Column(name = "TEACHER_COMMENT", length = 10240)
  private String teacherComment = "";
  @Lob
  @Column(name = "STUDENT_COMMENT", length = 10240)
  private String studentComment = "";

  @Column(name = "DAY_OF_WEEK")
  private Integer dayOfWeek = -1;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_WEEKLY_OVERVIEW_CLASS_UUID", nullable = false)
  private WeeklyOverviewClassEntity weeklyOverviewClass;
}
