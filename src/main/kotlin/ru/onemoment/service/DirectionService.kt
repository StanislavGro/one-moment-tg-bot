package ru.onemoment.service

import org.springframework.stereotype.Service
import ru.onemoment.repository.DirectionRepository

@Service
class DirectionService(
    private val directionRepository: DirectionRepository
) {

    fun getDirectionNames(id: Long): List<String> = directionRepository.findDirectionNamesBy(id)

}