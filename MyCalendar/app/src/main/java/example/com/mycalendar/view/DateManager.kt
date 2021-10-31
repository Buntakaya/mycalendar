package example.com.mycalendar.view

import example.com.mycalendar.config.Config.DefaultDateFormat
import example.com.mycalendar.config.Config.MonthFormat
import java.util.*

class DateManager {
    var mCalendar:Calendar = Calendar.getInstance()

    //当月のTextViewを設定
    fun getDays():MutableList<Date>{
        //現在の状態を保持
        val startDate=mCalendar.time

        //GridViewに表示するマスの合計を計算
        val count=getWeeks()*7

        //当月のカレンダーに表示される前月分の日数を計算
        mCalendar.set(Calendar.DATE,1)
        val dayOfWeek=mCalendar.get(Calendar.DAY_OF_WEEK)-1
        mCalendar.add(Calendar.DATE, -dayOfWeek)

        val days= mutableListOf<Date>()
        for(i in 0..count){
            days.add(mCalendar.time)
            mCalendar.add(Calendar.DATE, 1)
        }

        //状態を復元
        mCalendar.time=startDate
        return days
    }
    //当日かどうか確認
    fun isToday(date:Date):Boolean{
        val now = Calendar.getInstance()
        val today=DefaultDateFormat.format(now.time)
        return today == DefaultDateFormat.format(date)
    }

    //当月かどうか確認
    fun isCurrentMonth(date:Date):Boolean{
        val currentMonth=MonthFormat.format(mCalendar.time)
        return currentMonth == MonthFormat.format(date)
    }
    //週数の取得
    fun getWeeks():Int{
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH)
    }

    //曜日を取得
    fun getDayOfWeek(date:Date):Int{
        val calendar=Calendar.getInstance()
        calendar.time=date
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    //翌月へ
    fun nextMonth(){
        mCalendar.add(Calendar.MONTH, 1)
    }

    //前月へ
    fun prevMonth(){
        mCalendar.add(Calendar.MONTH, -1)
    }
}