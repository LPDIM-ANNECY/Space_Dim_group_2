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


    private val _eventIsInRoom = MutableLiveData<Boolean>()
    val eventIsInRoom: LiveData<Boolean>
        get() = _eventIsInRoom

    private val _eventSocketActive = MutableLiveData<Boolean>()
    val eventSocketActive: LiveData<Boolean>
        get() = _eventSocketActive

    private val _eventSwitchActivity = MutableLiveData<Boolean>()
    val eventSwitchActivity: LiveData<Boolean>
        get() = _eventSwitchActivity

    var vaisseauName = ""

    init {
    }

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

    fun joinRoom(name: String){
        vaisseauName = name
        _eventSocketActive.value = true
        _eventIsInRoom.value = true
        _eventDisplayPopupRoomName.value = false
        userRepository.currentUser.value?.let { webSocket.joinRoom(name, it) }
    }

    fun onDisplayPopupRoomName() {
        _eventDisplayPopupRoomName.value = true
    }

    fun onSwitchActivity() {
        _eventSwitchActivity.value = true
    }

}