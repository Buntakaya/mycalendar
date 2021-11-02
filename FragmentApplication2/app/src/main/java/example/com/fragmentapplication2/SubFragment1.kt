package example.com.fragmentapplication2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM1 = "Counter"


class SubFragment1 : Fragment() {
    var cnt=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sub1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args=arguments
        if (args!=null){
            cnt=args.getInt(ARG_PARAM1)
            val str="SubFragment1:$cnt"
            cnt++

            val textView=view.findViewById<TextView>(R.id.textview_01)
            textView.text=str
        }
        val button01=view.findViewById<Button>(R.id.button_01)
        button01.setOnClickListener {
            val fragmentManager=parentFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            val subFragment2=SubFragment2.newInstance(cnt)
            //BackStackを設定
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.container,subFragment2)
            fragmentTransaction.commit()
        }

        //BackStackで１つ戻す
        val buttonPop1=view.findViewById<Button>(R.id.pop_01)
        buttonPop1.setOnClickListener {
            val fragmentManager=parentFragmentManager
            fragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            SubFragment1().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}