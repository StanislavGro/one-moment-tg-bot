package ru.onemoment.teachers

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "schedule_id")
    @ManyToMany(mappedBy = "groupName")
    val schedule: Set<Schedule>
)
