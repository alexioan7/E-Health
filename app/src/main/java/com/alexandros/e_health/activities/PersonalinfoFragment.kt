package com.alexandros.e_health.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.databinding.FragmentPersonalinfoBinding
import com.alexandros.e_health.viewmodels.PersonalinfoViewModel

class PersonalinfoFragment : Fragment() {

    private lateinit var personalinfoViewModel: PersonalinfoViewModel
    private var _binding: FragmentPersonalinfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        personalinfoViewModel =
            ViewModelProvider(this).get(PersonalinfoViewModel::class.java)

        _binding = FragmentPersonalinfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        personalinfoViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}