package ru.onemoment.keyboards.builder

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Component
import ru.onemoment.keyboards.Keyboard
import ru.onemoment.keyboards.builder.interfaces.KeyboardBuilder
import ru.onemoment.service.ScheduleService
import ru.onemoment.service.TeacherService

@Component
class TeacherGroupKeyboardBuilder(
    private val teacherService: TeacherService,
    private val scheduleService: ScheduleService
) : KeyboardBuilder {
    override fun create(): InlineKeyboardMarkup {
        val teacher = teacherService.getCurrentTeacher()

        val teacherGroups = scheduleService.getTeacherGroups(teacher.id)

        val groupsInlineKeyboardButton = arrayListOf<InlineKeyboardButton>()

        for (group in teacherGroups) {
            groupsInlineKeyboardButton.add(
                InlineKeyboardButton.CallbackData(
                    text = group,
                    callbackData = group
                )
            )
        }

        val result = groupsInlineKeyboardButton
            .map { listOf(it) }
            .toMutableList()
            .apply {
                add(
                    listOf(
                        InlineKeyboardButton.CallbackData(
                            text = "Назад",
                            callbackData = "backToTeachers"
                        )
                    )
                )
            }


        return InlineKeyboardMarkup.create(result)
    }

    override fun isSupports(keyboard: Keyboard): Boolean =
        keyboard.name == "TEACHER_GROUP_KEYBOARD"
}