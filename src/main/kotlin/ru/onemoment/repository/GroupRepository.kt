package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Group

@Repository
interface GroupRepository: JpaRepository<Group, Long>