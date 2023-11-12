package ru.onemoment.utills

object VideoTitleConverter {
    fun getAbsoluteVideoPath(videoTitle: String?): String {
//        return "D:\\My programming\\one-moment-tg-bot\\src\\main\\resources\\teachers\\videos\\$videoTitle"
        return "/home/youngstanis/IdeaProjects/one-moment-tg-bot/src/main/resources/teachers/videos/$videoTitle"
    }
}