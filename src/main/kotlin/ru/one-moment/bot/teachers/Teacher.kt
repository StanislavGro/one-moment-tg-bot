package ru.`one-moment`.bot.teachers

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup

interface Teacher {
    val info: String
    val photo: String
    val schedule: List<String>
    val video: String?
    val keyboard: InlineKeyboardMarkup
}