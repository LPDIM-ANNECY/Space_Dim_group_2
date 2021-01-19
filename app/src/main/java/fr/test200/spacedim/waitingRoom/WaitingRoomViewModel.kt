package fr.test200.spacedim.waitingRoom

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.spacedim.dataClass.EditTextName
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.network.WSListener
import fr.test200.spacedim.repository.UserRepository
import retrofit2.HttpException

class WaitingRoomViewModel(userRepository: UserRepository, webSocket: WSListener) : ViewModel() {

    // userRepository
    val userRepository: UserRepository by lazy {
        userRepository
    }

    // userRepository
    val webSocket : WSListener by lazy {
        webSocket
    }

    private val _eventGoDashBoard = MutableLiveData<Boolean>()
    val eventGoDashBoard: LiveData<Boolean>
        get() = _eventGoDashBoard

    // recupération user list du websocket
    fun getWebSocketState(): LiveData<Event> = webSocket.webSocketState

    private val _eventDisplayPopupRoomName = MutableLiveData<Boolean>()
    val eventDisplayPopupRoomName: LiveData<Boolean>
        get() = _eventDisplayPopupRoomName

    init {
    }

    /**
     * Callback called when the ViewModel is destroyed
     */

    fun onGoDashboard() {
        _eventDisplayPopupRoomName.value = false
        _eventGoDashBoard.value = true
    }

    fun onGoDashboardComplete() {
        _eventGoDashBoard.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun joinRoom(name: String){

    }

    fun onDisplayPopupRoomName() {
        _eventDisplayPopupRoomName.value = true
    }

}