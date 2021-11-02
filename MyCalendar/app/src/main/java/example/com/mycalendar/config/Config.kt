package example.com.mycalendar.config

import example.com.mycalendar.dao.ScheduleDao
import java.text.SimpleDateFormat

object Config {
    val DefaultDateFormat=SimpleDateFormat("yyyy/MM/dd")
    val TimeForMat=SimpleDateFormat("HH:mm")
    val DayFormat=SimpleDateFormat("d")
    val MonthFormat= SimpleDateFormat("yyyy/MM")
    val scheduleDao= ScheduleDao()
}