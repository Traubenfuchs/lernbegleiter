package at.technikumwien.lernbegleiter.entities.reflection;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "WEEKLY_OVERVIEW_CLASS")
@Entity
public class WeeklyOverviewClassEntity extends BaseEntity<WeeklyOverviewClassEntity> {
    @Column(name = "COLOR")
    private String color;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_WEEKLY_OVERVIEW_UUID", nullable = false)
    private WeeklyOverviewEntity weeklyOverview;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
    private ClassEntity clazz;

    @OneToMany(mappedBy = "weeklyOverviewClass", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.JOIN)
    private Set<WeeklyOverviewClassDayEntity> days = new HashSet<>();
}
