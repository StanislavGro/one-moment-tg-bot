package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "days_of_week")
data class DayOfWeek(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToMany(mappedBy = "daysOfWeek")
    val schedule: List<Schedule>,
)
