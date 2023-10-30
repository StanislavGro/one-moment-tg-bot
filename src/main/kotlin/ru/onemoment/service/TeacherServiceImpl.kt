package ru.onemoment.service

import org.springframework.stereotype.Service
import ru.onemoment.entities.Teacher
import ru.onemoment.repository.TeacherRepository
import javax.annotation.PostConstruct

@Service
class TeacherServiceImpl(
    private val teacherRepository: TeacherRepository
) {
    private var teacherId: Int = 0
    private var teacherList: List<Teacher> = listOf()

    @PostConstruct
    private fun fillTeacherList() = refreshTeacherList()

    fun refreshTeacherList() {
        teacherList = teacherRepository.findAll()
        teacherId = 0
    }

    fun getCurrentTeacher(): Teacher = teacherList[teacherId]

    fun getNextTeacher(): Teacher {
        if(teacherId == teacherList.size - 1) {
            teacherId = 0
        } else {
            teacherId += 1
        }
        return teacherList[teacherId]
    }

    fun getPreviousTeacher(): Teacher {
        if(teacherId == 0) {
            teacherId = teacherList.size - 1
        } else {
            teacherId -= 1
        }
        return teacherList[teacherId]
    }
}
