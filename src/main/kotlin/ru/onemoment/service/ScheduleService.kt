package ru.onemoment.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import ru.onemoment.entities.Schedule
import ru.onemoment.entities.dto.DayTimeDto
import ru.onemoment.entities.enums.TableNames
import ru.onemoment.repository.ScheduleRepository

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
    private val jdbcTemplate: JdbcTemplate
) {

    fun getSchedulesByTeacherId(teacherId: Long): List<Schedule> =
        scheduleRepository.findScheduleByTeacherId(teacherId)

    fun getTeacherGroups(teacherId: Long): List<String> {
        val sqlQuery = """
                            SELECT
                                gr.name AS group_name
                            FROM ${TableNames.SCHEDULE.tableName} AS sch
                            JOIN ${TableNames.GROUPS.tableName} AS gr
                            ON gr.id IN (
                                SELECT group_id
                                FROM ${TableNames.SCHEDULE_GROUPS.tableName} AS sg
                                WHERE sg.schedule_id = sch.id
                                )
                            WHERE sch.teacher_id IN (
                                SELECT id
                                FROM ${TableNames.TEACHERS.tableName}
                                WHERE teacher_id = ?
                                )
                            GROUP BY group_name
                            ORDER BY group_name desc
                       """.trimIndent()


        return jdbcTemplate.query(
            sqlQuery,
            PreparedStatementSetter { ps ->
                ps.setLong(1, teacherId)
            },
            RowMapper { rs, _ -> rs.getString("group_name") }
        )
    }

    fun getDayAndTimeByTeacherId(teacherId: Long, groupName: String): List<String> {

        val sqlQuery = """
                            SELECT 
                                dof.name AS day_of_week,
                                sch.time_period
                            FROM ${TableNames.SCHEDULE.tableName} AS sch
                            JOIN ${TableNames.DAYS_OF_WEEK.tableName} AS dof
                            ON dof.id IN (
                                SELECT day_of_week_id
                                FROM ${TableNames.SCHEDULE_DAYS_OF_WEEK.tableName} AS sdof
                                WHERE sdof.schedule_id = sch.id
                                )
                            JOIN ${TableNames.GROUPS.tableName} AS gr
                            ON gr.id in (
                                SELECT group_id
                                FROM ${TableNames.SCHEDULE_GROUPS.tableName} AS sg
                                where sg.schedule_id = sch.id
                                ) and gr.name = ?
                            WHERE sch.teacher_id in (
                                SELECT id
                                FROM ${TableNames.TEACHERS.tableName}
                                WHERE teacher_id = ?
                                )
                            ORDER BY day_of_week, time_period;
                        """.trimIndent()

        val groupSchedules = jdbcTemplate.query(
            sqlQuery,
            PreparedStatementSetter { ps ->
                ps.setString(1, groupName)
                ps.setLong(2, teacherId)
            },
            RowMapper { rs, _ ->
                DayTimeDto(
                    dayOfWeek = rs.getString("day_of_week"),
                    timePeriod = rs.getString("time_period")
                )
            }
        ).map {
            "*${it.dayOfWeek}:* ${it.timePeriod}"
        }

        return groupSchedules
    }

//    fun getParsedScheduleByTeacherId(teacherId: Long): String {
//
//        val sqlQuery = """
//                    SELECT
//                        gr.name AS group_name,
//                        dof.name AS day_of_week,
//                        sch.time_period
//                    FROM ${TableNames.SCHEDULE.tableName} AS sch
//                    JOIN ${TableNames.DAYS_OF_WEEK.tableName} AS dof
//                    ON dof.id IN (
//                        select day_of_week_id
//                        from ${TableNames.SCHEDULE_DAYS_OF_WEEK.tableName} AS sdof
//                        where sdof.schedule_id = sch.id
//                        )
//                    JOIN ${TableNames.GROUPS.tableName} AS gr
//                    ON gr.id IN (
//                        SELECT group_id
//                        FROM ${TableNames.SCHEDULE_GROUPS.tableName} AS sg
//                        WHERE sg.schedule_id = sch.id
//                        )
//                    WHERE sch.teacher_id IN (
//                        select id
//                        from ${TableNames.TEACHERS.tableName}
//                        where teacher_id = ?
//                        )
//                    ORDER BY group_name desc, day_of_week, time_period
//                """.trimIndent()
//
//        val groupScheduleMap = jdbcTemplate.query(
//            sqlQuery,
//            PreparedStatementSetter { ps ->
//                ps.setLong(1, teacherId)
//            },
//            RowMapper { rs, _ ->
//                GroupScheduleDto(
//                    groupName = rs.getString("group_name"),
//                    dayOfWeek = rs.getString("day_of_week"),
//                    timePeriod = rs.getString("time_period")
//                )
//            }
//        ).groupBy {
//            it.groupName
//        }.mapValues { (_, groupSchedules) ->
//            groupSchedules.map {
//                "${it.dayOfWeek} ${it.timePeriod}"
//            }
//        }
//
//        var teacherScheduleInfo = ""
//
//        for ((group, schedules) in groupScheduleMap) {
//            teacherScheduleInfo += "*${group}*: ${schedules.joinToString()} \n\n"
//        }
//
//        return teacherScheduleInfo
//    }
}