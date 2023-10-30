package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Schedule

@Repository
interface ScheduleRepository: JpaRepository<Schedule, Long>