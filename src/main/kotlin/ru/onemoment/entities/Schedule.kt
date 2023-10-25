package ru.onemoment.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToMany
    @JoinTable (
        name = "schedule_day_of_week",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "day_of_week_id")]
    )
    val dayOfWeek: Set<DayOfWeek>,

    @Column(name = "time_period")
    val timePeriod: String,

    @ManyToOne
    val teacher: Teacher,

    @ManyToMany
    @JoinTable(
        name = "schedule_group",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val group: Set<Group>
)