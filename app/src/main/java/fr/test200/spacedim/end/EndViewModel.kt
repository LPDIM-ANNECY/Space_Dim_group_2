package fr.test200.spacedim.end

import androidx.lifecycle.ViewModel

class EndViewModel(finalScore: Int)  : ViewModel() {

    init {}

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}