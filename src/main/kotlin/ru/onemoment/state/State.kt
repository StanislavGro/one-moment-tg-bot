package ru.onemoment.state

import com.github.kotlintelegrambot.Bot

interface State {

    fun handleInput(input: String): State?

    fun enter(bot: Bot, chatId: Long)

}