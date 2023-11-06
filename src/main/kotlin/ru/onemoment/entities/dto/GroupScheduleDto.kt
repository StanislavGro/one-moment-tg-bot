package ru.onemoment.entities.dto

data class GroupScheduleDto(
    val groupName: String,
    val dayOfWeek: String,
    val timePeriod: String
)