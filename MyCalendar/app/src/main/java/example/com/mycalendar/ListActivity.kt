package example.com.mycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import example.com.mycalendar.config.Config
import example.com.mycalendar.databinding.ActivityListBinding
import example.com.mycalendar.dto.ScheduleDto

class ListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //クリックされた日付を取得
        val strDate: String? =intent.getStringExtra("date")

        binding.listActivityDateTextView.text=strDate

        //REGISTRATIONボタンを押したときの処理
        binding.listActivityRegistrationButton.setOnClickListener {
            val intent= Intent(applicationContext,RegistrationActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date",binding.listActivityDateTextView.text.toString())
            startActivity(intent)
        }

        //メイン画面遷移用テキストビューの処理
        binding.listActivityNavigationTextView.text="＜"+strDate!!.substring(0,7)
        val color= ContextCompat.getColor(this,R.color.aliceBlue)
        binding.listActivityNavigationTextView.setBackgroundColor(color)
        binding.listActivityNavigationTextView.setOnClickListener{
            val intent= Intent(applicationContext,MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("date",binding.listActivityDateTextView.text.toString())
            startActivity(intent)
        }

        //リストの処理
        val list= Config.scheduleDao.findByDate(applicationContext,strDate)
        val adapter= ArrayAdapter<ScheduleDto>(this,android.R.layout.simple_expandable_list_item_1)

        for (entity in list){
            adapter.add(entity)
        }
        binding.listActivityListView.adapter=adapter
        binding.listActivityListView.setOnItemClickListener { adapterView, _, i, _ ->
            val scheduleEntity=adapterView.getItemAtPosition(i) as ScheduleDto
            val intent=Intent(applicationContext,UpdateDeleteActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("id",scheduleEntity.id)
            startActivity(intent)
        }
    }
}