package ru.onemoment.components

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
data class BotProperties(
    var name: String = "",
    var token: String = "",
    var timeout: Int = 30
)