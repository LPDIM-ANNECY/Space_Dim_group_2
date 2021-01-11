package fr.test200.spacedim.end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EndViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EndViewModel::class.java)) {
            return EndViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}