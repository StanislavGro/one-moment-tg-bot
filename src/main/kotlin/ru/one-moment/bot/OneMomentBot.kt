package ru.`one-moment`.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.*
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                text = "Привет! Прежде чем начать, давай зарегистрируемся /login"
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
    }

    private fun Dispatcher.setUpCallbacks() {
        callbackQuery(callbackData = "prevTeacher") {
            if (teacherId == 0) {
                teacherId = teacherList.size - 1
            } else {
                teacherId--
            }

            bot.sendMessage(
                chatId = chatId,
                text = teacherList[teacherId],
                replyMarkup = inlineKeyboard,
                parseMode = ParseMode.MARKDOWN_V2
            )
        }

        callbackQuery(callbackData = "nextTeacher") {
            if(teacherId == teacherList.size - 1) {
                teacherId = 0
            } else {
                teacherId++
            }

            bot.sendMessage(
                chatId = chatId,
                text = teacherList[teacherId],
                replyMarkup = inlineKeyboard,
                parseMode = ParseMode.MARKDOWN_V2
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
                    callbackData = "prevTeacher"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "➡️",
                    callbackData = "nextTeacher"
                )
            )
        )
    }
}