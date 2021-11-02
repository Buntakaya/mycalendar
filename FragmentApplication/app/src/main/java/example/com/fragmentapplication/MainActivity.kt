package example.com.fragmentapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1=findViewById<Button>(R.id.button)

        //savedInstanceState==nullとは初回の時の処理
        if(savedInstanceState==null){
            button1.setOnClickListener {
                //FragmentManagerのインスタンス生成
                val fragmentManager=supportFragmentManager
                //FragmentTransactionのインスタンスを取得
                val fragmentTransaction=fragmentManager.beginTransaction()
                //SampleFragmentをパラメーター付きで設定
                val sampleFragment=SampleFragment.newInstance("Fragment")

                //BackStackを設定：戻れるようになる
                fragmentTransaction.addToBackStack(null)

                //パラメーターを設定
                //インスタンスに対して貼り付け方を指定する
                fragmentTransaction.replace(R.id.container,sampleFragment)
                //貼り付け実行
                fragmentTransaction.commit()
            }

        }
    }
}