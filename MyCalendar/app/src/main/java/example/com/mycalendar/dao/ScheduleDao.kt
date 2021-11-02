package example.com.mycalendar.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import example.com.mycalendar.dao.ScheduleContract.Schedule.SCHEDULE_DATE
import example.com.mycalendar.dao.ScheduleContract.Schedule.SCHEDULE_DETAIL
import example.com.mycalendar.dao.ScheduleContract.Schedule.SCHEDULE_TIME
import example.com.mycalendar.dao.ScheduleContract.Schedule.SCHEDULE_TITLE
import example.com.mycalendar.dao.ScheduleContract.Schedule.TABLE_NAME
import example.com.mycalendar.dto.InsertDto
import example.com.mycalendar.dto.ScheduleDto

class ScheduleDao {

    private fun getReadableDatabase(context: Context): SQLiteDatabase = ScheduleDbOpenHelper(context).readableDatabase

    private fun getWritableDatabase(context: Context): SQLiteDatabase=ScheduleDbOpenHelper(context).writableDatabase

    private fun createSchedule(cursor: Cursor): ScheduleDto {
        return ScheduleDto(
            cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DATE)),
            cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_TIME)),
            cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DETAIL))
        )
    }

    private fun createList(cursor: Cursor):MutableList<ScheduleDto>{
        val list: MutableList<ScheduleDto> = mutableListOf()
        if (cursor.moveToFirst()){
            do {
                list.add(createSchedule(cursor))
            }while (cursor.moveToNext())
        }
        return list
    }

    private fun createContentValues1(scheduleDto: ScheduleDto):ContentValues{
        val contentValues= ContentValues()
        contentValues.put(BaseColumns._ID,scheduleDto.id)
        contentValues.put(SCHEDULE_DATE,scheduleDto.date)
        contentValues.put(SCHEDULE_TIME,scheduleDto.time)
        contentValues.put(SCHEDULE_TITLE,scheduleDto.title)
        contentValues.put(SCHEDULE_DETAIL,scheduleDto.detail)
        return contentValues
    }

    private fun createContentValues2(insertDto: InsertDto):ContentValues{
        val contentValues=ContentValues()
        contentValues.put(SCHEDULE_DATE,insertDto.date)
        contentValues.put(SCHEDULE_TIME,insertDto.time)
        contentValues.put(SCHEDULE_TITLE,insertDto.title)
        contentValues.put(SCHEDULE_DETAIL,insertDto.detail)
        return contentValues
    }

    //日付検索
    fun findByDate(context: Context,date:String):MutableList<ScheduleDto>{
        val db=getReadableDatabase(context)

        //抽出条件
        val selection = "$SCHEDULE_DATE = ?"
        val selectionArgs = arrayOf(date)

        //並べ替え条件
        val sortOrder = "$SCHEDULE_DATE,$SCHEDULE_TIME"

        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        val scheduleDtoList=createList(cursor)
        cursor.close()
        return scheduleDtoList
    }

    //ID検索
    fun findById(context: Context,id:Long):ScheduleDto?{
        val db=getReadableDatabase(context)

        //抽出条件
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var scheduleDto:ScheduleDto?=null
        if (cursor.moveToFirst()){
            scheduleDto=createSchedule(cursor)
        }
        cursor.close()
        return scheduleDto
    }

    //スケジュール全件検索
    fun getDateList(context: Context,date: String):MutableList<String>{
        val db=getReadableDatabase(context)

        //列リスト
        val projection = arrayOf(SCHEDULE_DATE)

        //抽出条件
        val selection = "$SCHEDULE_DATE LIKE ?"
        val selectionArgs = arrayOf(date)

        //並べ替え条件
        val sortOrder = "$SCHEDULE_DATE"

        val cursor=db.query(
            true,
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder,
            null
        )

        val list: MutableList<String> = mutableListOf()
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(cursor.getColumnIndexOrThrow(SCHEDULE_DATE)))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    //登録処理
    fun insert(context: Context,insertDto:InsertDto):Long{
        val db=getWritableDatabase(context)
        return db.insert(TABLE_NAME,null,createContentValues2(insertDto))
    }

    //更新処理
    fun update(context: Context, scheduleDto: ScheduleDto):Int{
        val db=getWritableDatabase(context)
        return db.update(TABLE_NAME,createContentValues1(scheduleDto), "${BaseColumns._ID}=?", arrayOf(scheduleDto.id.toString()))
    }

    //削除処理
    fun delete(context: Context, id: Long): Int {
        val db = getWritableDatabase(context)
        return db.delete(TABLE_NAME, "${BaseColumns._ID}=?", arrayOf(id.toString()))
    }
}