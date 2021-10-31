package example.com.mycalendar.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import example.com.mycalendar.R
import example.com.mycalendar.config.Config
import example.com.mycalendar.config.Config.DayFormat
import example.com.mycalendar.config.Config.DefaultDateFormat
import example.com.mycalendar.config.Config.MonthFormat
import java.text.DateFormat
import java.text.SimpleDateFormat

import java.util.*

class CalendarAdapter(private val mContext: Context): BaseAdapter() {
    var dateArray= mutableListOf<Date>()
    private var inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mDateManager: DateManager = DateManager()

    init{
        dateArray=mDateManager.getDays()
    }

    override fun getCount(): Int {
        return dateArray.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView=view
        val holder: ViewHolder
        if (convertView==null){
            convertView = inflater.inflate(R.layout.calendar_cell, null)
            val dateText=convertView!!.findViewById<TextView>(R.id.dateText)
            holder= ViewHolder(dateText)
            convertView.tag=holder
        }else{
            holder=convertView.tag as ViewHolder
        }

        //セルのサイズを指定
        val dp=mContext.resources.displayMetrics.density
        convertView.layoutParams= AbsListView.LayoutParams(parent!!.width / 7 - dp.toInt(), (parent.height - dp.toInt() * mDateManager.getWeeks()) / mDateManager.getWeeks())

        //日付のみ表示させる
        holder.dateText.text=DayFormat.format(dateArray[position])

        //スケジュールがある日は★の画像、それ以外は何もなし
        val date=DefaultDateFormat.format(dateArray[position])
        val imagePoint= convertView.findViewById<ImageView>(R.id.imagePoint)
        imagePoint.visibility= if(isSchedule(date)){
            View.VISIBLE
        }else{
            View.INVISIBLE
        }

        //当月以外のセルをグレーアウト
        if(mDateManager.isCurrentMonth(dateArray[position])){
            convertView.setBackgroundColor(Color.WHITE)
        }else{
            convertView.setBackgroundColor(Color.LTGRAY)
        }

        //今日を黄色に
        if (mDateManager.isToday(dateArray[position])){
            convertView.setBackgroundColor(Color.YELLOW)
        }

        //日曜日を赤、土曜日を青に
        val colorId=when(mDateManager.getDayOfWeek(dateArray[position])){
            1 -> Color.RED
            7 -> Color.BLUE
            else->Color.BLACK
        }
        holder.dateText.setTextColor(colorId)
        return convertView
    }

    //スケジュールがあるかどうか
    private fun isSchedule(date:String):Boolean{
        val list= Config.scheduleDao.getDateList(mContext,date)
        return list.size != 0
    }

    //表示月を取得
    fun getTitle(selectedDate: String? =null):String{
        return MonthFormat.format(mDateManager.mCalendar.time)
    }

    //翌月表示
    fun nextMonth() {
        mDateManager.nextMonth()
        dateArray = mDateManager.getDays()
        notifyDataSetChanged()
    }

    //前月表示
    fun prevMonth() {
        mDateManager.prevMonth()
        dateArray = mDateManager.getDays()
        notifyDataSetChanged()
    }
}