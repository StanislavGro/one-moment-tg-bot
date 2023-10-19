package ru.onemoment

import com.github.kotlintelegrambot.entities.BotCommand
import ru.onemoment.bot.OneMomentBot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Application {
    companion object {
        val botCommands: List<BotCommand> =
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
    }
}

fun main(args: Array<String>) {

    runApplication<Application>(*args)

    val oneMomentBot = OneMomentBot().createBot()
    oneMomentBot.setMyCommands(Application.botCommands)
    oneMomentBot.startPolling()
}