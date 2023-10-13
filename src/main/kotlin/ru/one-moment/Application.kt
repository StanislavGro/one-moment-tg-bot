package ru.`one-moment`

import com.github.kotlintelegrambot.entities.BotCommand
import ru.`one-moment`.bot.OneMomentBot

private val botCommands: List<BotCommand> =
    listOf(
        BotCommand("/start", "Запуск бота"),
        BotCommand("/teachers", "Посмотреть всех преподавателей"),
        BotCommand("/login", "Зарегистрироваться"),
        BotCommand("/schedule", "Мое расписание (Доступно после регистрации)"),
        BotCommand("/group", "Моя группа (Доступно после регистрации)"),
        BotCommand("/pay", "Оплатить абонемент (Доступно после регистрации)"),
        BotCommand("/trial", "Записаться на пробное занятие (Доступно после регистрации)"),
        BotCommand("/help", "Список команд бота")
    )

fun main(args: Array<String>) {
//    args.forEach {
//        println(it)
//    }

    val oneMomentBot = OneMomentBot().createBot()
    oneMomentBot.setMyCommands(botCommands)
    oneMomentBot.startPolling()
}