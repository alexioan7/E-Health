package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel

class PrescriptionsFragment : Fragment() {

    private lateinit var prescriptionsViewModel: PrescriptionsViewModel
    private var _binding: FragmentPrescriptionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prescriptionsViewModel =
            ViewModelProvider(this).get(PrescriptionsViewModel::class.java)

        _binding = FragmentPrescriptionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        prescriptionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}