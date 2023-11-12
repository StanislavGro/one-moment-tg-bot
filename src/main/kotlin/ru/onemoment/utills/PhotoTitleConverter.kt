package ru.onemoment.utills

object PhotoTitleConverter {
    fun getAbsolutePhotoPath(photoTitle: String?): String {
//        return "D:\\My programming\\one-moment-tg-bot\\src\\main\\resources\\teachers\\photos\\$photoTitle"
        return "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teachers/photos/$photoTitle"
    }
}