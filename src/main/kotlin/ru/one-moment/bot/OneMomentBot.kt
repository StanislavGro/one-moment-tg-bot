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
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.logging.LogLevel
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

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacherPhoto[teacherId])),
                caption = teacherList[teacherId],
                replyMarkup = nextPrevKeyboardButton,
                parseMode = ParseMode.MARKDOWN
            )

//            bot.sendMessage(
//                chatId = chatId,
//                text = teacherList[teacherId],
//                replyMarkup = nextPrevKeyboardButton,
//                parseMode = ParseMode.MARKDOWN
//            )
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

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacherPhoto[teacherId])),
                caption = teacherSchedule[0],
                replyMarkup = nextPrevKeyboardButton,
                parseMode = ParseMode.MARKDOWN
            )

//            bot.editMessageText(
//                chatId = chatId,
//                messageId = messageId?.plus(1L),
//                replyMarkup = nextPrevKeyboardButton,
//                parseMode = ParseMode.MARKDOWN,
//                text = teacherList[teacherId],
//            )
        }

        callbackQuery(callbackData = "nextTeacher") {
            if (teacherId == teacherList.size - 1) {
                teacherId = 0
            } else {
                teacherId++
            }

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacherPhoto[teacherId])),
                caption = teacherSchedule[0],
                replyMarkup = nextPrevKeyboardButton,
                parseMode = ParseMode.MARKDOWN
            )

//            bot.editMessageText(
//                chatId = chatId,
//                messageId = messageId?.plus(1L),
//                replyMarkup = nextPrevKeyboardButton,
//                parseMode = ParseMode.MARKDOWN,
//                text = teacherList[teacherId],
//            )
        }

//        callbackQuery(callbackData = "teacherSchedule") {
//
//            bot.editMessageText(
//                chatId = chatId,
//                messageId = messageId,
//                text = teacherInfo[teacherId],
//                parseMode = ParseMode.MARKDOWN,
//                replyMarkup = romixxScheduleKeyboardButton
//            )
//
//        }

        callbackQuery(callbackData = "romixJunior") {

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacherPhoto[teacherId])),
                caption = teacherSchedule[0],
                replyMarkup = nextPrevKeyboardButton,
                parseMode = ParseMode.MARKDOWN
            )

//            bot.editMessageText(
//                chatId = chatId,
//                messageId = messageId,
//                text = teacherSchedule[0],
//                parseMode = ParseMode.MARKDOWN,
//                replyMarkup = nextPrevKeyboardButton
//            )
        }

        callbackQuery(callbackData = "romixMiddle") {

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(teacherPhoto[teacherId])),
                caption = teacherSchedule[1],
                replyMarkup = nextPrevKeyboardButton,
                parseMode = ParseMode.MARKDOWN
            )

//            bot.editMessageText(
//                chatId = chatId,
//                messageId = messageId,
//                text = teacherSchedule[1],
//                parseMode = ParseMode.MARKDOWN,
//                replyMarkup = nextPrevKeyboardButton
//            )
        }

    }

    companion object {
        private var teacherId = 0

        private val teacherList = listOf(
            """
                *Romixx* 🕺
                
                Направление *hip-hop*
                Доступны 2 группы: 6-9 лет и 10-13 лет
            """.trimIndent(),
            "Наш *второй* препод",
            "Наш *третий* препод"
        )

        private val teacherPhoto = listOf(
            "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-photos/romixx.jpg",
            "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-photos/romixx.jpg",
            "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-photos/romixx.jpg"
        )

        private val teacherSchedule = listOf(
            "6-9 лет, понедельник, четверг с 17:00 до 18:00",
            "10-13 лет понедельник, четверг с 20:00 до 21:00",
        )

        val nextPrevKeyboardButton = InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "6-9 лет",
                    callbackData = "romixJunior"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "10-13 лет",
                    callbackData = "romixMiddle"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Видео превью",
                    callbackData = "videoPreview"
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

        val romixxScheduleKeyboardButton = InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "6-9 лет",
                    callbackData = "romix69"
                ),
                InlineKeyboardButton.CallbackData(
                    text = "10-13 лет",
                    callbackData = "romix1013"
                )
            ),
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Назад",
                    callbackData = "back"
                )
            )
        )
    }
}