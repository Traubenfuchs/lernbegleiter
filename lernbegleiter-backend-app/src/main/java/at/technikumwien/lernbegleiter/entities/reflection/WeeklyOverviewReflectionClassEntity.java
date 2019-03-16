package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@Table(name = "WEEKLY_OVERVIEW_REFLECTION_CLASS")
@Entity
public class WeeklyOverviewReflectionClassEntity extends BaseEntity<WeeklyOverviewReflectionClassEntity> {
    private String color = "";
    @Column(length = 10240)
    private String progress = "";
    @Column(length = 10240)
    private String improvements = "";

    @ManyToOne
    @JoinColumn(name = "WEEKLY_OVERVIEW_UUID", nullable = false)
    private WeeklyOverviewEntity weeklyOverview;
}
