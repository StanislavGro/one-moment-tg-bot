package ru.onemoment.keyboards.builder

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Component
import ru.onemoment.keyboards.Keyboard
import ru.onemoment.keyboards.builder.interfaces.KeyboardBuilder

@Component
class TeacherKeyboardBuilder: KeyboardBuilder {

    override fun create(): InlineKeyboardMarkup =
        InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Расписание",
                    callbackData = "schedule"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Видео превью",
                    callbackData = "videoPreview"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "⬅️",
                    callbackData = "prevTeacher"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "➡️",
                    callbackData = "nextTeacher"
                )
            )
        )

    override fun isSupports(keyboard: Keyboard): Boolean =
        keyboard.name == "TEACHER_KEYBOARD"
}