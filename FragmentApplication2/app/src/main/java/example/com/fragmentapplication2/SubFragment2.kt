package example.com.fragmentapplication2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM1 = "Counter"

class SubFragment2 : Fragment() {
    private var cnt : Int= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cnt  = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sub2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments!=null){
            val str="SubFragment:$cnt"
            cnt++

            val textView=view.findViewById<TextView>(R.id.textview_02)
            textView.text=str
        }

        val button02=view.findViewById<Button>(R.id.button_02)
        button02.setOnClickListener {
            val fragmentManager=parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val subFragment1=SubFragment2.newInstance(cnt)
            //BackStackを設定
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.container,subFragment1)
            fragmentTransaction.commit()
        }

        //BackStackで１つ戻す
        val buttonPop2=view.findViewById<Button>(R.id.pop_02)
        buttonPop2.setOnClickListener {
            val fragmentManager=parentFragmentManager
            fragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            SubFragment2().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}