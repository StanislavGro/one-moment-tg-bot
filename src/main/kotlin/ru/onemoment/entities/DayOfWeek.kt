package ru.onemoment.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class DayOfWeek(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToOne
    val schedule: Schedule
)
