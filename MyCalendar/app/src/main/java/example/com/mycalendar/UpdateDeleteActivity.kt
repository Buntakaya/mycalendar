package example.com.mycalendar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import example.com.mycalendar.config.Config
import example.com.mycalendar.config.Config.DefaultDateFormat
import example.com.mycalendar.config.Config.TimeForMat
import example.com.mycalendar.databinding.ActivityUpdateDeleteBinding
import example.com.mycalendar.dto.ScheduleDto
import java.text.ParseException
import java.util.*

class UpdateDeleteActivity : AppCompatActivity() {
    private lateinit var mSchedule: ScheduleDto
    private lateinit var binding:ActivityUpdateDeleteBinding
    val TAG=UpdateDeleteActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateDeleteBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //インテントに選択されたIDを取得
        val id=intent.getLongExtra("id",0)
        //データベース検索
        mSchedule = Config.scheduleDao.findById(applicationContext, id)!!
        //colors.xmlから背景色を取得
        val colorAliceBlue= ContextCompat.getColor(this,R.color.aliceBlue)
        //ビューとレイアウトの設定
        binding.updateDeleteActivityNavigationTextView.text=mSchedule.date
        val str="＜ " + mSchedule.date
        binding.updateDeleteActivityNavigationTextView.text=str
        binding.updateDeleteActivityNavigationTextView.setBackgroundColor(colorAliceBlue)
        binding.updateDeleteActivityNavigationTextView.setOnClickListener{
            //インテントに選択された日付をセット
            val intent= Intent(applicationContext,ListActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date", mSchedule.date)
            //アクティビティをスタート
            startActivity(intent)
        }
        binding.updateDeleteActivityDateTextView.text=mSchedule.date
        binding.updateDeleteActivityDateTextView.setBackgroundColor(Color.WHITE)
        binding.updateDeleteActivityDateTextView.setOnClickListener {

            val calendar = Calendar.getInstance()
            try {
                calendar.time = DefaultDateFormat.parse(mSchedule.date)
            } catch (e: ParseException) {
                Log.e(TAG, e.message.toString())
            }
            val datePickerDialog = DatePickerDialog(
                it.context,
                { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(year, monthOfYear, dayOfMonth)
                    mSchedule.date = DefaultDateFormat.parse(calendar.time.toString()).toString()
                    binding.updateDeleteActivityDateTextView.text = mSchedule.date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.updateDeleteActivityTimeTextView.text=mSchedule.time
        binding.updateDeleteActivityTimeTextView.setBackgroundColor(Color.WHITE)
        binding.updateDeleteActivityTimeTextView.setOnClickListener{
            val calendar=Calendar.getInstance()
            try {
                calendar.time= TimeForMat.parse(mSchedule.date)
            }catch (e: ParseException){
                Log.e(TAG,e.message.toString())
            }
            Log.d(TAG, "h " + calendar.get(Calendar.HOUR_OF_DAY))
            Log.d(TAG, "m " + calendar.get(Calendar.MINUTE))

            val timePickerDialog= TimePickerDialog(
                it.context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    mSchedule.time= TimeForMat.format(calendar.time.toString())
                    binding.updateDeleteActivityTimeTextView.text=mSchedule.time},
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true)
            timePickerDialog.show()
        }

        binding.updateDeleteActivityTitleEditText.setText(mSchedule.title)
        binding.updateDeleteActivityTitleEditText.setBackgroundColor(Color.WHITE)
        binding.updateDeleteActivityDetailEditText.setText(mSchedule.detail)
        binding.updateDeleteActivityDetailEditText.setBackgroundColor(Color.WHITE)

        binding.updateDeleteActivityUpdateButton.setOnClickListener {
            //データベースを更新
            mSchedule.date=binding.updateDeleteActivityDateTextView.text.toString()
            mSchedule.time=binding.updateDeleteActivityTimeTextView.text.toString()
            mSchedule.title=binding.updateDeleteActivityTitleEditText.text.toString()
            mSchedule.detail=binding.updateDeleteActivityDetailEditText.text.toString()
            Config.scheduleDao.update(applicationContext, mSchedule)
            //インテントに選択された日付をセット
            val intent=Intent(applicationContext,ListActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date", mSchedule.date)
            //アクティビティをスタート
            startActivity(intent)
        }

        binding.updateDeleteActivityDeleteButton.setOnClickListener {
            val builder= AlertDialog.Builder(it.context)
            builder.setMessage("削除しますか")
            builder.setNegativeButton("Cancel", null)
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                //データベースから削除
                Config.scheduleDao.delete(applicationContext, mSchedule.id)
                //インテントに選択された日付をセット
                val intent=Intent(applicationContext,ListActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("date", mSchedule.date)
                //アクティビティをスタート
                startActivity(intent)
            })
            builder.create().show()
        }
    }

}