package ru.onemoment.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.onemoment.entities.Schedule

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Long> {

    fun findScheduleByTeacherId(id: Long): List<Schedule>

//    @Query("""
//            SELECT
//                groups.name AS group_name,
//                days_of_week.name AS day_of_week,
//                schedule.time_period
//            FROM schedule
//            JOIN days_of_week
//            ON days_of_week.id IN (
//                select day_of_week_id
//                from schedule_days_of_week
//                where schedule_days_of_week.schedule_id = schedule.id
//                )
//            JOIN groups
//            ON groups.id IN (
//                SELECT group_id
//                FROM schedule_groups
//                WHERE schedule_groups.schedule_id = schedule.id
//                )
//            WHERE schedule.teacher_id IN (
//                select id
//                from teachers
//                where teacher_id = :teacher_id
//                )
//            ORDER BY group_name desc, day_of_week, time_period
//        """, nativeQuery = true)
//    fun findGroupNameAndDayOfWeekAndTimePeriodBy(@Param("teacher_id") teacherId: Long): List<GroupSchedule>

}