package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Direction

@Repository
interface DirectionRepository: JpaRepository<Direction, Long> {

    @Query("select d.name from directions d where d.id = :id")
    fun findDirectionNamesBy(id: Long): List<String>

}
