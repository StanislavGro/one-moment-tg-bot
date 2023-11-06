package ru.onemoment.service

import org.springframework.stereotype.Service
import ru.onemoment.repository.GroupRepository

@Service
class GroupService(
    private val groupRepository: GroupRepository
) {

    fun getGroupNameByScheduleId(id: Long): String =
        groupRepository.findGroupNameBy(id)

}