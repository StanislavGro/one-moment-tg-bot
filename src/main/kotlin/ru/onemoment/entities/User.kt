package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "child_id")
    val childId: Long,

    @Column(name = "name")
    val name:String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    @ManyToMany
    @JoinTable(
        name = "users_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    val groups: List<Group>
)
