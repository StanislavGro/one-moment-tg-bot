package ru.onemoment.state

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Update
import ru.onemoment.state.adduser.InitialState

class StateMachine(private val bot: Bot) {

    private var currentState: State = InitialState()

    fun handleUpdate(update: Update) {
        if(update.message != null) {
            val message = update.message
            val nextState = currentState.handleInput(message?.text.orEmpty())
            if(nextState != null) {
                val chatId = message?.chat?.id ?: throw NoSuchElementException("Failed to get chatId")
                currentState = nextState
                currentState.enter(bot, chatId)
            }
        }
    }

}