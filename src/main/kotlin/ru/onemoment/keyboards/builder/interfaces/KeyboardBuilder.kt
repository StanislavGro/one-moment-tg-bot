package ru.onemoment.keyboards.builder.interfaces

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import ru.onemoment.keyboards.Keyboard

interface KeyboardBuilder {
    fun create(): InlineKeyboardMarkup
    fun isSupports(keyboard: Keyboard): Boolean
}