package example.com.mycalendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.com.mycalendar.databinding.FragmentCalendarBinding
import example.com.mycalendar.view.CalendarAdapter

class CalendarFragment : Fragment() {
    private var _binding:FragmentCalendarBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mCalendarAdapter= CalendarAdapter(requireActivity().applicationContext)
        binding.calendarGridView.adapter=mCalendarAdapter
        binding.titleText.text=mCalendarAdapter.getTitle()


        //日付をクリックした時
        binding.calendarGridView.setOnItemClickListener { _, _, _, _ ->
            val fragmentManager=parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.fragment, RegistrationFragment())
            fragmentTransaction.commit()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}