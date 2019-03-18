package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_CLASS_DAY")
@Entity
public class WeeklyOverviewClassDayEntity extends BaseEntity<WeeklyOverviewClassDayEntity> {
    @Column(name = "TEACHER_COMMENT", length = 10240)
    private String teacherComment = "";
    @Column(name = "STUDENT_COMMENT", length = 10240)
    private String studentComment = "";

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_WEEKLY_OVERVIEW_CLASS_UUID", nullable = false)
    private WeeklyOverviewClassEntity weeklyOverviewClass;
}
