package example.com.mycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import example.com.mycalendar.config.Config.DefaultDateFormat
import example.com.mycalendar.databinding.ActivityMainBinding
import example.com.mycalendar.view.CalendarAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mCalendarAdapter= CalendarAdapter(this)
        binding.calendarGridView.adapter=mCalendarAdapter
        binding.titleText.text=mCalendarAdapter.getTitle()


        //日付をクリックした時
        binding.calendarGridView.setOnItemClickListener { adapterView, _, i, _ ->
            //GridViewからアダプタービューを取得
            val intent= Intent(applicationContext,ListActivity::class.java)
            val adapter=adapterView.adapter as CalendarAdapter
            //クリックされた日付を受け渡し
            val date=DefaultDateFormat.format(adapter.dateArray[i])
            intent.putExtra("date",date)
            startActivity(intent)
        }

        //Nextボタンの処理
        binding.nextButton.setOnClickListener {
            mCalendarAdapter.nextMonth()
            binding.titleText.text=mCalendarAdapter.getTitle()
        }

        //Prevボタンの処理
        binding.prevButton.setOnClickListener {
            mCalendarAdapter.prevMonth()
            binding.titleText.text=mCalendarAdapter.getTitle()
        }
    }
}