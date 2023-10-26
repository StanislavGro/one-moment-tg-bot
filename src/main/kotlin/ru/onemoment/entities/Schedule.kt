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
import javax.persistence.OneToMany

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "day_of_week_id")
    @OneToMany(mappedBy = "schedule_id")
    val dayOfWeek: Set<DayOfWeek>,

    @Column(name = "time_period")
    val timePeriod: String,

    @ManyToOne
    val teacher: Teacher,

    @Column(name = "group_id")
    @OneToMany(mappedBy = "schedule_id")
    val group: Set<Group>
)