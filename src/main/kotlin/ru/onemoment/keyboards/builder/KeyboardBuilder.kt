package ru.onemoment.keyboards.builder

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import ru.onemoment.keyboards.Keyboard

interface KeyboardBuilder {
    fun create(): InlineKeyboardMarkup
    fun isSupports(keyboard: Keyboard): Boolean
}