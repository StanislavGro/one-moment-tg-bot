package ru.onemoment.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Teacher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "direction")
    val direction: String?,

    @Column(name = "info")
    val info: String?,

    @Column(name = "schedule_id")
    @OneToMany(mappedBy = "teacher")
    val schedule: Set<Schedule>,

    @Column(name = "photo_title")
    val photoTitle: String?,

    @Column(name = "video_title")
    val videoTitle: String?
)