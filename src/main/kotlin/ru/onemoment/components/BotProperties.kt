package ru.onemoment.components

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
data class BotProperties(
    val name: String = "",
    val token: String = "",
    val timeout: Int = 30
)