package example.com.mycalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarFragment=CalendarFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment,calendarFragment)
        fragmentTransaction.commit()

        /*val mCalendarAdapter= CalendarAdapter(this)
        binding.calendarGridView.adapter=mCalendarAdapter
        binding.titleText.text=mCalendarAdapter.getTitle()


        //日付をクリックした時
        binding.calendarGridView.setOnItemClickListener { adapterView, _, i, _ ->
            val registrationFragment=RegistrationFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment,registrationFragment)
            transaction.commit()
*//*            //GridViewからアダプタービューを取得
            val intent= Intent(applicationContext,ListActivity::class.java)
            val adapter=adapterView.adapter as CalendarAdapter
            //クリックされた日付を受け渡し
            val date=DefaultDateFormat.format(adapter.dateArray[i])
            intent.putExtra("date",date)
            startActivity(intent)*//*
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
        }*/
    }
}