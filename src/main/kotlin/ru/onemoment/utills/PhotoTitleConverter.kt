package ru.onemoment.utills

object PhotoTitleConverter {
    fun getAbsolutePhotoPath(photoTitle: String?): String {
//        return "D:\\My programming\\one-moment-tg-bot\\src\\main\\resources\\teachers\\photos\\$photoTitle"
        return "classpath:teachers/photos/$photoTitle"
    }
}