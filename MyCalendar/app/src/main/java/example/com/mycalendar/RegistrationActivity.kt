package example.com.mycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import example.com.mycalendar.config.Config
import example.com.mycalendar.config.Config.DefaultDateFormat
import example.com.mycalendar.config.Config.TimeForMat
import example.com.mycalendar.databinding.ActivityRegistrationBinding
import example.com.mycalendar.dto.InsertDto
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        var selectDate=intent.getStringExtra("date")

        if (selectDate==null){
            selectDate=DefaultDateFormat.format(Date())
        }

        //スケジュール一覧表示画面遷移用テキストビュー
        val color= ContextCompat.getColor(this,R.color.aliceBlue)
        val str="＜$selectDate"
        binding.registrationActivityNavigationTextView.text=str
        binding.registrationActivityNavigationTextView.setBackgroundColor(color)
        binding.registrationActivityNavigationTextView.setOnClickListener {
            val intent= Intent(applicationContext,ListActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date",binding.registrationActivityDateTextView.text)
            startActivity(intent)
        }

        binding.registrationActivityDateTextView.text=selectDate
        binding.registrationActivityTimePicker.setIs24HourView(true)

        //REGISTRATIONボタンの処理
        binding.registrationActivityButton.setOnClickListener{
            val title=findViewById<EditText>(R.id.registrationActivity_title_editText).text.toString()
            val detail=findViewById<EditText>(R.id.registrationActivity_detail_editText).text.toString()
            if (title.trim().isEmpty()){
                Snackbar.make(view,"Title is required",LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (detail.trim().isEmpty()){
                Snackbar.make(view,"Detail is required",LENGTH_LONG).show()
                return@setOnClickListener
            }

            val calendar=Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY,binding.registrationActivityTimePicker.currentHour)
            calendar.set(Calendar.MINUTE,binding.registrationActivityTimePicker.currentMinute)

            val mSchedule= InsertDto(
                binding.registrationActivityDateTextView.text.toString(),
                TimeForMat.format(calendar.time),
                title,
                detail
            )
            Config.scheduleDao.insert(applicationContext,mSchedule)
            val intent=Intent(applicationContext,ListActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date",binding.registrationActivityDateTextView.text.toString())
            startActivity(intent)
        }
    }
}