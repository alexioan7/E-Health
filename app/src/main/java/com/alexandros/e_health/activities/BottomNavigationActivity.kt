package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alexandros.e_health.R
import com.alexandros.e_health.databinding.FragmentPersonalinfoBinding
import com.alexandros.e_health.databinding.FragmentPrescriptionsBinding
import com.alexandros.e_health.databinding.FragmentPrescriptionsShareBinding
import com.alexandros.e_health.repositories.AuthRepository
import com.alexandros.e_health.viewmodels.PersonalinfoViewModel
import com.alexandros.e_health.viewmodels.PrescriptionsShareViewModel
import com.alexandros.e_health.viewmodels.PrescriptionsViewModel
import com.alexandros.e_health.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_bottom_navigation.*



class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var navController: NavController
   private lateinit var viewModel: PersonalinfoViewModel
   private lateinit var binding: FragmentPersonalinfoBinding
   private lateinit var prescriptionsViewModel: PrescriptionsViewModel
   private lateinit var prescriptionsBinding: FragmentPrescriptionsBinding
    private lateinit var prescriptionsShareViewModel:  PrescriptionsShareViewModel
    private lateinit var prescriptionsShareBinding: FragmentPrescriptionsShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        binding = FragmentPersonalinfoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, ViewModelFactory(AuthRepository)).get(PersonalinfoViewModel::class.java)

        prescriptionsBinding = FragmentPrescriptionsBinding.inflate(layoutInflater)
        prescriptionsViewModel= ViewModelProvider(this, ViewModelFactory(AuthRepository)).get(PrescriptionsViewModel::class.java)

        prescriptionsShareBinding = FragmentPrescriptionsShareBinding.inflate(layoutInflater)
        prescriptionsShareViewModel= ViewModelProvider(this, ViewModelFactory(AuthRepository)).get(PrescriptionsShareViewModel::class.java)

        binding.personalinfoviewmodel = viewModel

        prescriptionsBinding.prescriptionsviewmodel = prescriptionsViewModel




        navController = findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation)
        nav_view.setupWithNavController(navController)

    }
}