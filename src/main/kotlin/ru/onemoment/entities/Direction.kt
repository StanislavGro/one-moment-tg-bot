package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "directions")
data class Direction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToMany(mappedBy = "directions")
    val teachers: List<Teacher>,
)