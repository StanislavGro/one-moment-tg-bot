package ru.onemoment.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatAction
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
import ru.onemoment.utills.PhotoTitleConverter
import ru.onemoment.utills.VideoTitleConverter
import java.io.File

@Component
class OneMomentBot(
    private val botProperties: BotProperties,
    private val teacherService: TeacherService,
    private val keyboardFactory: KeyboardFactory,
    private val scheduleService: ScheduleService
) {

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
            val chatId = ChatId.fromId(message.chat.id)

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

            val chatId = ChatId.fromId(message.chat.id)
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

        command("login") {

            val chatId = ChatId.fromId(message.chat.id)

            // Проверяем есть ли в бд такой пользоатель
            // Если есть, то ты уже зареган
            // Если нет, то давай ка зарегайся

            bot.sendMessage(
                chatId = chatId,
                text = "Скоро сделаем :)",
            )
        }

        command("help") {
            val chatId = ChatId.fromId(message.chat.id)

            val helpText = """
                    *Список команд:*
                    /start - запустить бота
                    /teachers - посмотреть учителей и направления
                    /login - зарегистрироваться

                    /help - посмотреть все команды бота
                """.trimIndent()

//                    *Доступно после регистрации:*
//                    /schedule - мое расписание
//                    /group - моя группа
//                    /trial - записаться на пробное занятие
//                    /pay - оплатить абонемент

            bot.sendMessage(
                chatId = chatId,
                text = helpText,
                parseMode = ParseMode.MARKDOWN,
            )
        }
    }

    private fun Dispatcher.setUpCallbacks() {
        callbackQuery(callbackData = "prevTeacher") {
            val teacher = teacherService.getPreviousTeacher()

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
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

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "videoPreview") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Подожди немного, отправляем \uD83D\uDE09",
                parseMode = ParseMode.MARKDOWN,
            )

            bot.sendChatAction(
                chatId = ChatId.fromId(chatId),
                action = ChatAction.UPLOAD_VIDEO
            )

            bot.sendVideo(
                chatId = ChatId.fromId(chatId),
                video = TelegramFile.ByFile(
                    File(VideoTitleConverter.getAbsoluteVideoPath(teacher.videoTitle))
                ),
                width = 720,
                height = 1280,
                replyMarkup = keyboardFactory.create(Keyboard.BackToTeacher())
            )

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId + 1
            )
        }

        callbackQuery(callbackData = "login") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery

            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Скоро сделаем :)"
            )
        }

        callbackQuery(callbackData = "schedule") {

            val teacher = teacherService.getCurrentTeacher()

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = "Доступны *следующие* группы:",
                replyMarkup = keyboardFactory.create(Keyboard.TeacherGroup()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "backToTeacher") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = teacherService.getInfo(),
                replyMarkup = keyboardFactory.create(Keyboard.TeacherKeyboard()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "6-9 лет") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "6-9 лет")

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "10-13 лет") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "10-13 лет")

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "Krump 8+") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "Krump 8+")

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "Lil'problems clan") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "Lil'problems clan")

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
                photo = TelegramFile.ByFile(
                    File(PhotoTitleConverter.getAbsolutePhotoPath(teacher.photoTitle))
                ),
                caption = groupScheduleInfo.joinToString(separator = "\n"),
                replyMarkup = keyboardFactory.create(Keyboard.BackToSchedule()),
                parseMode = ParseMode.MARKDOWN
            )
        }

        callbackQuery(callbackData = "One moment clan") {

            val chatId = callbackQuery.message?.chat?.id ?: return@callbackQuery
            val messageId = callbackQuery.message?.messageId ?: return@callbackQuery

            val teacher = teacherService.getCurrentTeacher()
            val groupScheduleInfo = scheduleService.getDayAndTimeByTeacherId(teacher.id, "One moment clan")

            bot.deleteMessage(
                chatId = ChatId.fromId(chatId),
                messageId = messageId
            )

            bot.sendPhoto(
                chatId = ChatId.fromId(chatId),
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