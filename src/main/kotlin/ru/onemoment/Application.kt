package ru.onemoment

import com.github.kotlintelegrambot.entities.BotCommand
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import ru.onemoment.bot.OneMomentBot
import javax.annotation.PostConstruct


private val botCommands: List<BotCommand> = listOf(
    BotCommand("/start", "Запуск бота"),
    BotCommand("/teachers", "Посмотреть всех преподавателей"),
    BotCommand("/login", "Зарегистрироваться"),
    BotCommand("/schedule", "Мое расписание (Доступно после регистрации)"),
    BotCommand("/group", "Моя группа (Доступно после регистрации)"),
    BotCommand("/pay", "Оплатить абонемент (Доступно после регистрации)"),
    BotCommand("/trial", "Записаться на пробное занятие (Доступно после регистрации)"),
    BotCommand("/help", "Список команд бота")
)

@SpringBootApplication
class Application ( private val oneMomentBot: OneMomentBot ) {

    @PostConstruct
    private fun startBot() {
        val startedBot = oneMomentBot.createBot()
        startedBot.setMyCommands(botCommands)
        startedBot.startPolling()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
