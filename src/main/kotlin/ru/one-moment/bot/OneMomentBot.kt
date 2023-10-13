package ru.`one-moment`.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.logging.LogLevel

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
            logLevel = LogLevel.Error

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
                text = """
                    Привет и добро пожаловать 🎉
                    Для начала давай зарегистрируемся и увеличим функционал бота
                """.trimIndent()
            )

            bot.sendMessage(
                chatId = chatId,
                text = "Нажимай на /login"
            )
        }

        command("teachers") {
            _chatId = ChatId.fromId(message.chat.id)
            messageId = message.messageId

            bot.sendMessage(
                chatId = chatId,
                text = teacherList[teacherId],
                replyMarkup = inlineKeyboard,
                parseMode = ParseMode.MARKDOWN_V2
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
            if (teacherId == 0) {
                teacherId = teacherList.size - 1
            } else {
                teacherId--
            }

            bot.editMessageText(
                chatId = chatId,
                messageId = messageId?.plus(1L),
                replyMarkup = inlineKeyboard,
                parseMode = ParseMode.MARKDOWN_V2,
                text = teacherList[teacherId],
            )
        }

        callbackQuery(callbackData = "nextTeacher") {
            if (teacherId == teacherList.size - 1) {
                teacherId = 0
            } else {
                teacherId++
            }

            bot.editMessageText(
                chatId = chatId,
                messageId = messageId?.plus(1L),
                replyMarkup = inlineKeyboard,
                parseMode = ParseMode.MARKDOWN_V2,
                text = teacherList[teacherId],
            )
        }
    }

    companion object {
        private var teacherId = 0

        private val teacherList = listOf(
            "Наш *первый* препод",
            "Наш *второй* препод",
            "Наш *третий* препод"
        )

        val inlineKeyboard = InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "⬅️",
//                    text = "<",
                    callbackData = "prevTeacher"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "➡️",
//                    text = ">",
                    callbackData = "nextTeacher"
                )
            )
        )
    }
}