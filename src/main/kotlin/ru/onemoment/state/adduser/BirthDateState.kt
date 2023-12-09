package ru.onemoment.state.adduser

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import ru.onemoment.state.State

class BirthDateState : State {

    override fun handleInput(input: String): State? {

        return null
    }

    override fun enter(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Супер! Информация сохранена"
        )
    }

}