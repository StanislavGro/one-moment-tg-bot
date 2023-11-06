package ru.onemoment.keyboards

sealed class Keyboard {
    abstract val name: String

    class TeacherKeyboard : Keyboard() {
        override val name: String = "TEACHER_KEYBOARD"
    }

    class Login : Keyboard() {
        override val name: String = "LOGIN_KEYBOARD"
    }

    class TeacherGroup: Keyboard() {
        override val name: String = "TEACHER_GROUP_KEYBOARD"
    }

    class BackToTeacher : Keyboard() {
        override val name: String = "BACK_TO_TEACHER_KEYBOARD"
    }

    class BackToSchedule : Keyboard() {
        override val name: String = "BACK_TO_SCHEDULE_KEYBOARD"
    }
}
