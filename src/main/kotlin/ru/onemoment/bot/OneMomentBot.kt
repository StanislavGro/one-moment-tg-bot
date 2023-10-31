package ru.onemoment.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.logging.LogLevel
import org.springframework.stereotype.Component
import ru.onemoment.components.BotProperties
import ru.onemoment.service.TeacherServiceImpl
import ru.onemoment.service.impl.TeacherService
import ru.onemoment.utills.Keyboards
import ru.onemoment.utills.PhotoTitleConverter
import java.io.File

@Component
class OneMomentBot(
    private val botProperties: BotProperties,
    private val teacherService: TeacherServiceImpl
) {

    private var _chatId: ChatId? = null
    private val chatId by lazy { requireNotNull(_chatId) }

    private var messageId: Long? = null

    fun createBot(): Bot {
        return bot {
            timeout = botProperties.timeout
            token = botProperties.token
            logLevel = LogLevel.Error

            dispatch {
                setUpCommands()
//                setUpCallbacks()
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

            val teacher = teacherService.getCurrentTeacher()

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))),
                caption = teacher.info,
//                replyMarkup = teacher.keyboard,
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

//    private fun Dispatcher.setUpCallbacks() {
//        callbackQuery(callbackData = "prevTeacher") {
//            val teacher = OneMomentTeachers.previous()
//
//            messageId = messageId?.plus(1)
//
//            bot.deleteMessage(
//                chatId = chatId,
//                messageId = messageId!!
//            )
//
//            bot.sendPhoto(
//                chatId = chatId,
//                photo = TelegramFile.ByFile(File(teacher.photo)),
//                caption = teacher.info,
//                replyMarkup = teacher.keyboard,
//                parseMode = ParseMode.MARKDOWN
//            )
//        }
//
//        callbackQuery(callbackData = "nextTeacher") {
//            val teacher = OneMomentTeachers.next()
//
//            messageId = messageId?.plus(1)
//
//            bot.deleteMessage(
//                chatId = chatId,
//                messageId = messageId!!
//            )
//
//            bot.sendPhoto(
//                chatId = chatId,
//                photo = TelegramFile.ByFile(File(teacher.photo)),
//                caption = teacher.info,
//                replyMarkup = teacher.keyboard,
//                parseMode = ParseMode.MARKDOWN
//            )
//        }
//
//        callbackQuery(callbackData = "romanPreview") {
//            bot.sendVideo(
//                chatId = chatId,
//                video = TelegramFile.ByFile(File(Roman.video)),
//            )
//        }
//
//        callbackQuery(callbackData = "junior") {
//
//            messageId = messageId?.plus(1)
//
//            bot.deleteMessage(
//                chatId = chatId,
//                messageId = messageId!!
//            )
//
//            bot.sendPhoto(
//                chatId = chatId,
//                photo = TelegramFile.ByFile(File(Roman.photo)),
//                caption = Roman.schedule[0],
//                replyMarkup = Roman.keyboard,
//                parseMode = ParseMode.MARKDOWN
//            )
//        }
//
//        callbackQuery(callbackData = "middle") {
//
//            messageId = messageId?.plus(1)
//
//            bot.deleteMessage(
//                chatId = chatId,
//                messageId = messageId!!
//            )
//
//            bot.sendPhoto(
//                chatId = chatId,
//                photo = TelegramFile.ByFile(File(Roman.photo)),
//                caption = Roman.schedule[1],
//                replyMarkup = Roman.keyboard,
//                parseMode = ParseMode.MARKDOWN
//            )
//        }
//
//    }
}