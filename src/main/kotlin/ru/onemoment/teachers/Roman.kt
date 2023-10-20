//package ru.onemoment.teachers
//
//import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
//import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
//
//object Roman : TeacherInfo {
//    override val info: String
//        get() =
//            """
//                *Роман Панин* aka *Romixx* 🕺
//
//                Направление *HIP-HOP*
//                Доступны 2 группы: 6-9 лет и 10-13 лет
//            """.trimIndent()
//
//
//    override val schedule: List<String>
//        get() = listOf(
//            "6-9 лет, понедельник, четверг с 17:00 до 18:00",
//            "10-13 лет понедельник, четверг с 20:00 до 21:00",
//        )
//
//    override val photo: String
//        //        get() = "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-photos/Roman_Panin.jpg"
//        get() = "D:\\My programming\\one-moment-tg-bot\\src\\main\\resources\\teacher-photos\\Roman_Panin.jpg"
//
//
//    override val video: String?
//        get() = "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teacher-videos/Roman_Panin.mp4"
//
//    override val keyboard: InlineKeyboardMarkup
//        get() = InlineKeyboardMarkup.create(
//            listOf(
//                InlineKeyboardButton.CallbackData(
//                    text = "Расписание",
//                    callbackData = "romanSchedule"
//                )
//            ),
////            listOf(
////                InlineKeyboardButton.CallbackData(
////                    text = "6-9 лет",
////                    callbackData = "junior"
////                ),
////                InlineKeyboardButton.CallbackData(
////                    text = "10-13 лет",
////                    callbackData = "middle"
////                )
////            ),
//            listOf(
//                InlineKeyboardButton.CallbackData(
//                    text = "Видео превью",
//                    callbackData = "romanPreview"
//                )
//            ),
////            listOf(
////                InlineKeyboardButton.CallbackData(
////                    text = "Назад",
////                    callbackData = "back"
////                )
////            ),
//            listOf(
//                InlineKeyboardButton.CallbackData(
//                    text = "⬅️",
//                    callbackData = "prevTeacher"
//                ),
//                InlineKeyboardButton.CallbackData(
//                    text = "➡️",
//                    callbackData = "nextTeacher"
//                )
//
//            )
//        )
//}