package example.com.fragmentapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//Bundleに格納する際に必要なキーを設定
private const val ARG_PARAM1 = "param1"

class SampleFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args=arguments
        if (args!=null){
            val str =args.getString(ARG_PARAM1)
            val textView=view.findViewById<TextView>(R.id.text_fragment)
            textView.text=str
        }
    }

    companion object {
        //他フラグメントやアクティビティからデータを貰うため
        @JvmStatic
        fun newInstance(param1: String) =
            SampleFragment().apply {
                //Bundleにパラメーターを設定
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}