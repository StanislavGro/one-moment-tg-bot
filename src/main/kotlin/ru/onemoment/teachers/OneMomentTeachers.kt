//package ru.onemoment.teachers
//
//object OneMomentTeachers{
//
//    private var teacherId: Int = 0
//    private val teachers: List<TeacherInfo> = listOf(
//        Roman,
//        Victor
//    )
//
//    fun current(): TeacherInfo = teachers[teacherId]
//
//    fun next(): TeacherInfo {
//        if(teacherId == teachers.size - 1) {
//            teacherId = 0
//        } else {
//            teacherId += 1
//        }
//
//        return teachers[teacherId]
//    }
//
//    fun previous(): TeacherInfo {
//        if(teacherId == 0) {
//            teacherId = teachers.size - 1
//        } else {
//            teacherId -= 1
//        }
//
//        return teachers[teacherId]
//    }
//}