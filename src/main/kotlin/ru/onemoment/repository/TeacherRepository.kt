package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Teacher

@Repository
interface TeacherRepository: JpaRepository<Teacher, Long> {

    @Query("select t.info from teachers t where t.id = :id")
    fun getTeacherInfoBy(id: Long): String

    @Query("select t.photoTitle from teachers t where t.id = :id")
    fun getTeacherPhotoTitleBy(id: Long): String

    @Query("select t.videoTitle from teachers t where t.id = :id")
    fun getTeacherVideoTitleBy(id: Long): String

}