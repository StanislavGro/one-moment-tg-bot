package ru.onemoment.keyboards

import org.springframework.stereotype.Component
import ru.onemoment.keyboards.builder.KeyboardBuilder

@Component
class KeyboardFactory(val keyboardBuilders: List<KeyboardBuilder>) {
    fun create(keyboard: Keyboard) = keyboardBuilders.firstOrNull {
        it.isSupports(keyboard)
    }?.create()
        ?: throw NoSuchMethodError("No builder to create InlineKeyboard for ${keyboard.name}")
}