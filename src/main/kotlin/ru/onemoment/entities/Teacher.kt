package ru.onemoment.entities

import javax.persistence.*

@Entity(name = "teachers")
data class Teacher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "teachers_directions",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "direction_id")]
    )
    val directions: List<Direction>,

    @Column(name = "info")
    val info: String?,

    @Column(name = "schedule_id")
    @OneToMany(mappedBy = "teacher")
    val schedule: List<Schedule>,

    @Column(name = "photo_title")
    val photoTitle: String?,

    @Column(name = "video_title")
    val videoTitle: String?,
)