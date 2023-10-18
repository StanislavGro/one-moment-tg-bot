package ru.`one-moment`.bot

import com.github.kotlintelegrambot.Bot
import ru.`one-moment`.bot.keyboards.Keyboards
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.logging.LogLevel
import ru.`one-moment`.bot.teachers.OneMomentTeachers
import ru.`one-moment`.bot.teachers.Roman
import java.io.File

private const val BOT_ANSWER_TIMEOUT = 30
private const val BOT_TOKEN = "6554932402:AAEYN8oYHnUmp4yWY5mnSFjkr7MnmkiVCqE"

class OneMomentBot {

    private var _chatId: ChatId? = null
    private val chatId by lazy { requireNotNull(_chatId) }

    private var messageId: Long? = null

    fun createBot(): Bot {
        return bot {
            timeout = BOT_ANSWER_TIMEOUT
            token = BOT_TOKEN
            logLevel = LogLevel.All()

            dispatch {
                setUpCommands()
                setUpCallbacks()
            }

        }
    }

    private fun Dispatcher.setUpCommands() {

        command("start") {
            _chatId = ChatId.fromId(message.chat.id)

            bot.sendMessage(
                chatId = chatId,
                text = "Привет и добро пожаловать 🎉"
            )

            Thread.sleep(500)

            bot.sendMessage(
                chatId = chatId,
                text = "Для начала давай зарегистрируемся",
                replyMarkup = Keyboards.login
            )
        }

        command("teachers") {
            _chatId = ChatId.fromId(message.chat.id)
            messageId = message.messageId

            val teacher = OneMomentTeachers.current()

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacher.photo)),
                caption = teacher.info,
                replyMarkup = teacher.keyboard,
                parseMode = ParseMode.MARKDOWN
            )
        }

        command("pay") {

        }

        command("help") {
            _chatId = ChatId.fromId(message.chat.id)
            messageId = message.messageId

            val helpText = """
                    *Список команд:*
                    /start - запустить бота
                    /teachers - посмотреть учителей и направления
                    /login - зарегистрироваться
                    
                    *Доступно после регистрации:*
                    /schedule - мое расписание
                    /group - моя группа
                    /trial - записаться на пробное занятие
                    /pay - оплатить абонемент
                    
                    /help - посмотреть все команды бота
                """.trimIndent()
            bot.sendMessage(
                chatId = ChatId.fromId(message.chat.id),
                text = helpText,
                parseMode = ParseMode.MARKDOWN,
            )
        }
    }

    private fun Dispatcher.setUpCallbacks() {
        callbackQuery(callbackData = "prevTeacher") {
            val teacher = OneMomentTeachers.previous()

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacher.photo)),
                caption = teacher.info,
                replyMarkup = teacher.keyboard,
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "nextTeacher") {
            val teacher = OneMomentTeachers.next()

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacher.photo)),
                caption = teacher.info,
                replyMarkup = teacher.keyboard,
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "romanPreview") {
            bot.sendVideo(
                chatId = chatId,
                video = TelegramFile.ByFile(File(Roman.video)),
            )
        }

        callbackQuery(callbackData = "junior") {

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(Roman.photo)),
                caption = Roman.schedule[0],
                replyMarkup = Roman.keyboard,
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "middle") {

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(Roman.photo)),
                caption = Roman.schedule[1],
                replyMarkup = Roman.keyboard,
                parseMode = ParseMode.MARKDOWN
            )
        }

    }
}