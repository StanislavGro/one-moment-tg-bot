package ru.`one-moment`

import com.github.kotlintelegrambot.entities.BotCommand
import ru.`one-moment`.bot.OneMomentBot

fun main(args: Array<String>) {
//    args.forEach {
//        println(it)
//    }

    val oneMomentBot = OneMomentBot().createBot()
    oneMomentBot.setMyCommands(
        listOf(
            BotCommand("/start", "Запуск бота"),
            BotCommand("/teachers", "Посмотреть всех преподавателей"),
            BotCommand("/help", "Список команд")
        )
    )
    oneMomentBot.startPolling()
}