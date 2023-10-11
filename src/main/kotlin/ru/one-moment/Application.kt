package ru.`one-moment`

import ru.`one-moment`.bot.OneMomentBot

fun main(args: Array<String>) {
    val oneMomentBot = OneMomentBot().createBot()
    oneMomentBot.startPolling()
}