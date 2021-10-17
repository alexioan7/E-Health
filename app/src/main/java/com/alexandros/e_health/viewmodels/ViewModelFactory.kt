package com.alexandros.e_health.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandros.e_health.repositories.AuthRepository

class ViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthScreenViewModel::class.java) -> {
                modelClass.cast(AuthScreenViewModel(repository)) as T
            }
            modelClass.isAssignableFrom(PersonalinfoViewModel::class.java) -> {
                modelClass.cast(PersonalinfoViewModel(repository)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}