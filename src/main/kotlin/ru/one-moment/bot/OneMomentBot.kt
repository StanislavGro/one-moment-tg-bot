package ru.`one-moment`.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot

private const val BOT_ANSWER_TIMEOUT = 30
private const val BOT_TOKEN = "6554932402:AAEYN8oYHnUmp4yWY5mnSFjkr7MnmkiVCqE"

class OneMomentBot {

    fun createBot(): Bot {
        return bot {
            timeout = BOT_ANSWER_TIMEOUT
            token = BOT_TOKEN
        }
    }

}