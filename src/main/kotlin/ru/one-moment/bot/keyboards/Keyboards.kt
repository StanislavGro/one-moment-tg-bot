package ru.`one-moment`.bot.keyboards


import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Keyboards {
    val login: InlineKeyboardMarkup = InlineKeyboardMarkup.create(
        listOf(
            InlineKeyboardButton.CallbackData(
                text = "Зарегистрироваться",
                callbackData = "login"
            )
        )
    )

    val pagination: InlineKeyboardMarkup = InlineKeyboardMarkup.create(
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
}