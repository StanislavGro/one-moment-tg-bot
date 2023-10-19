package ru.onemoment.bot.teachers

object OneMomentTeachers{

    private var teacherId: Int = 0
    private val teachers: List<Teacher> = listOf(
        Roman,
        Victor
    )

    fun current(): Teacher = teachers[teacherId]

    fun next(): Teacher {
        if(teacherId == teachers.size - 1) {
            teacherId = 0
        } else {
            teacherId += 1
        }

        return teachers[teacherId]
    }

    fun previous(): Teacher {
        if(teacherId == 0) {
            teacherId = teachers.size - 1
        } else {
            teacherId -= 1
        }

        return teachers[teacherId]
    }
}