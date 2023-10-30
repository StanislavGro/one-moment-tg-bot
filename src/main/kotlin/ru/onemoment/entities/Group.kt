package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToMany(mappedBy = "groups")
    val schedule: List<Schedule>,

    @ManyToMany(mappedBy = "groups")
    val users: List<User>,
)
