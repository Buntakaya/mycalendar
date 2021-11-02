package example.com.fragmentapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState==null){
            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val subFragment1=SubFragment1.newInstance(0)

            // BackStackを設定
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.container,subFragment1)
            fragmentTransaction.commit()
        }
    }
}