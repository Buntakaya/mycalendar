package example.com.mycalendar.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class ScheduleDbOpenHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME="schedule.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreateEntries =
            "CREATE TABLE ${ScheduleContract.Schedule.TABLE_NAME}(" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${ScheduleContract.Schedule.SCHEDULE_DATE} TEXT,"+
                    "${ScheduleContract.Schedule.SCHEDULE_TIME} TEXT,"+
                    "${ScheduleContract.Schedule.SCHEDULE_TITLE} TEXT,"+
                    "${ScheduleContract.Schedule.SCHEDULE_DETAIL} TEXT)"
        db.execSQL(sqlCreateEntries)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}