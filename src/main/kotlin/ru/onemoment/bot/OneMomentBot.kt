package ru.onemoment.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.logging.LogLevel
import org.springframework.stereotype.Component
import ru.onemoment.components.BotProperties
import ru.onemoment.keyboards.Keyboard
import ru.onemoment.keyboards.KeyboardFactory
import ru.onemoment.service.ScheduleService
import ru.onemoment.service.TeacherService
//import ru.onemoment.utills.Keyboards
import ru.onemoment.utills.PhotoTitleConverter
import java.io.File

@Component
class OneMomentBot(
    private val botProperties: BotProperties,
    private val teacherService: TeacherService,
    private val keyboardFactory: KeyboardFactory,
    private val scheduleService: ScheduleService
) {

    private var _chatId: ChatId? = null
    private val chatId by lazy { requireNotNull(_chatId) }

    private var messageId: Long? = null

    fun createBot(): Bot {
        return bot {
            timeout = botProperties.timeout
            token = botProperties.token
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
                replyMarkup = keyboardFactory.create(Keyboard.Login())
            )
        }

        command("teachers") {
            _chatId = ChatId.fromId(message.chat.id)
            messageId = message.messageId

            val teacher = teacherService.getCurrentTeacher()

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
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
            val teacher = teacherService.getPreviousTeacher()

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "nextTeacher") {
            val teacher = teacherService.getNextTeacher()

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "login") {
            bot.sendMessage(
                chatId = chatId,
                text = "Скоро сделаем :)"
            )
        }

        callbackQuery(callbackData = "schedule") {

            val teacher = teacherService.getCurrentTeacher()

//            val schedule = scheduleService.getParsedScheduleByTeacherId(teacher.id)

            messageId = messageId?.plus(1)

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
//                caption = schedule,
                replyMarkup = keyboardFactory.create(Keyboard.TeacherGroup()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "backToTeacher") {
            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "6-9 лет") {

            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "6-9 лет")

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "10-13 лет") {

            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "10-13 лет")

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "Krump 8+") {

            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "Krump 8+")

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "Lil'problems clan") {

            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "Lil'problems clan")

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "One moment clan") {

            messageId = messageId?.plus(1)

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "One moment clan")

            bot.deleteMessage(
                chatId = chatId,
                messageId = messageId!!
            )

            bot.sendPhoto(
                chatId = chatId,
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

    }

}