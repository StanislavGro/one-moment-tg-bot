package ru.onemoment.state.adduser

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import ru.onemoment.state.State

class InitialState : State {

    override fun handleInput(input: String): State {
        if (input.equals("/login", ignoreCase = true)) {

        }
        return NameAndSurnameState()
    }


    override fun enter(bot: Bot, chatId: Long) {
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Давай познакомимся, напиши свое имя и фамилию"
        )
    }
}