package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "schedule")
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToMany
    @JoinTable(
        name = "schedule_days_of_week",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "day_of_week_id")]
    )
    val daysOfWeek: List<DayOfWeek>,

    @Column(name = "time_period")
    val timePeriod: String,

    @ManyToOne
    val teacher: Teacher,

    @ManyToMany
    @JoinTable(
        name = "schedule_groups",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val groups: List<Group>,
)