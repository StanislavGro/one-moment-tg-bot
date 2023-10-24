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

    val

    @Column(name = "time_period")
    val timePeriod: String,

//    @JoinColumn(name="teacher_id")
//    @Column(name = "teacher_id")
    @ManyToOne
    val teacher: Teacher,

//    @Column(name = "group_id")
    @ManyToMany
    @JoinTable(
        name = "schedule_group",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val group: Set<Group>
)