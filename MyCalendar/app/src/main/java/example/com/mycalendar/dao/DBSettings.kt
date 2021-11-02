package example.com.mycalendar.dao

import android.provider.BaseColumns

//このアプリで使用するテーブル一覧
object ScheduleContract{
    //スケジュールテーブルの列名やテーブル名
    object Schedule : BaseColumns{
        const val TABLE_NAME = "schedule"
        const val SCHEDULE_DATE = "schedule_date"
        const val SCHEDULE_TIME = "schedule_time"
        const val SCHEDULE_TITLE = "schedule_title"
        const val SCHEDULE_DETAIL = "schedule_detail"
    }
}