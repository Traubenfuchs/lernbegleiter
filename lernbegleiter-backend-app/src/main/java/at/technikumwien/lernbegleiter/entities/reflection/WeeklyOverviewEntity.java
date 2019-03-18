package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW")
@Entity
public class WeeklyOverviewEntity extends BaseEntity<WeeklyOverviewEntity> {
    @Column(name = "KW", nullable = false)
    private Integer kw;
    @Column(name = "MY_WEEKLY_GOALS", length = 10240)
    private String myWeeklyGoals = "";
    @Column(name = "FURTHER_STEPS", length = 10240)
    private String furtherSteps = "";

    @OneToMany(mappedBy = "weeklyOverview")
    private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

    @OneToMany(mappedBy = "weeklyOverview")
    private Set<WeeklyOverviewReflectionClassEntity> reflexionClasses = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
    private UserEntity student;
}
