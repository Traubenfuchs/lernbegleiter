package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW")
@Entity
public class WeeklyOverviewEntity extends BaseEntity<WeeklyOverviewEntity> {
    @Column(name = "KW", nullable = false)
    private Integer kw;
    @Lob
    @Column(name = "MY_WEEKLY_GOALS", length = 10240)
    private String myWeeklyGoals = "";
    @Lob
    @Column(name = "FURTHER_STEPS", length = 10240)
    private String furtherSteps = "";

    @OneToMany(mappedBy = "weeklyOverview", fetch = EAGER)
    @Fetch(value= FetchMode.JOIN)
    private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

    @OneToMany(mappedBy = "weeklyOverview", fetch = EAGER)
    @Fetch(value= FetchMode.JOIN)
    private Set<WeeklyOverviewReflectionClassEntity> reflexionClasses = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
    private UserEntity student;
}
