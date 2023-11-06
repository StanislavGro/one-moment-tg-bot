package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Group

@Repository
interface GroupRepository : JpaRepository<Group, Long> {

    @Query("select g.name from groups g where schedule = :scheduleId")
    fun findGroupNameBy(scheduleId: Long): String
}