package ru.onemoment.state.adduser

import com.github.kotlintelegrambot.Bot
import ru.onemoment.state.State

class NameAndSurnameState : State {

    override fun handleInput(input: String): State? {

        return BirthDateState()
    }

    override fun enter(bot: Bot, chatId: Long) {
        TODO("Not yet implemented")
    }

}