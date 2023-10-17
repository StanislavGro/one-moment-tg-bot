package ru.`one-moment`.bot.teachers

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton

object Victor : Teacher {
    override val info: String
        get() =
            """
                *Виктор Смараков* 🕺
                
                Направление: *HIP-HOP*, *KRUMP*
                Группы: krump 8+, Lil'problems clan, One moment clan
            """.trimIndent()


    override val schedule: List<String>
        get() = listOf(
            "krump 8+ ( понедельник, пятница с 20:00 до 21:00)",
            "Lil'problems clan вторник, четверг ( с 18:00 до 19:00) и среда с 19:00 до 20:00",
            "One moment clan (понедельник, пятница с 21:00 до 23:00) среда с 20:30 до 22:30"
        )

    override val photo: String
        get() = "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-photos/Victor_Smarakov.jpg"


    override val video: String?
        get() = null
//        get() = "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-videos/Victor_Smarakov.mp4"

    override val keyboard: InlineKeyboardMarkup
        get() = InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "krump 8+",
                    callbackData = "krumpEightPlus"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "Lil'problems clan",
                    callbackData = "lilProblemsClan"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "One moment clan",
                    callbackData = "oneMomentClan"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Видео превью",
                    callbackData = "preview"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Назад",
                    callbackData = "back"
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
}