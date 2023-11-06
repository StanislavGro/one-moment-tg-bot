package ru.onemoment.keyboards.builder

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Component
import ru.onemoment.keyboards.Keyboard

@Component
class BackToTeacherKeyboardBuilder : KeyboardBuilder {
    override fun create(): InlineKeyboardMarkup =
        InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Назад",
                    callbackData = "backToTeacher"
                )
            )
        )

    override fun isSupports(keyboard: Keyboard): Boolean =
        keyboard.name == "BACK_TO_TEACHER_KEYBOARD"
}