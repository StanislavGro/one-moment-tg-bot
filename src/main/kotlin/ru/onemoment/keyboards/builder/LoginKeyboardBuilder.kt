package ru.onemoment.keyboards.builder

import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Component
import ru.onemoment.keyboards.Keyboard

@Component
class LoginKeyboardBuilder: KeyboardBuilder {

    override fun create(): InlineKeyboardMarkup =
        InlineKeyboardMarkup.create(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "Login",
                    callbackData = "login"
                )
            )
        )

    override fun isSupports(keyboard: Keyboard): Boolean =
        keyboard.name == "LOGIN_KEYBOARD"
}