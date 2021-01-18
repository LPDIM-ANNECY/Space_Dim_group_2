package fr.test200.spacedim.waitingRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WaitingRoomViewModel : ViewModel() {


    private val _eventGoDashBoard = MutableLiveData<Boolean>()
    val eventGoDashBoard: LiveData<Boolean>
        get() = _eventGoDashBoard


    init {}

    /**
     * Callback called when the ViewModel is destroyed
     */

    fun onGoDashboard() {
        _eventGoDashBoard.value = true
    }

    fun onGoDashboardComplete() {
        _eventGoDashBoard.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}