package fr.test200.spacedim.waitingRoom

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.spacedim.dataClass.*
import fr.test200.spacedim.network.WSListener
import fr.test200.spacedim.repository.UserRepository
import retrofit2.HttpException

class WaitingRoomViewModel(userRepository: UserRepository, webSocket: WSListener) : ViewModel() {

    val userRepository: UserRepository by lazy {
        userRepository
    }

    val webSocket: WSListener by lazy {
        webSocket
    }

    // recupération etat du websocket
    fun getWebSocketState(): LiveData<Event> = webSocket.webSocketState

    fun getUserState(): LiveData<State> = userRepository.currentUserState

    private val _eventWaitingRoomStatus = MutableLiveData<EventType>()
    val eventWaitingRoomStatus: LiveData<EventType>
        get() = _eventWaitingRoomStatus

    private val _eventSocketActive = MutableLiveData<Boolean>()
    val eventSocketActive: LiveData<Boolean>
        get() = _eventSocketActive

    private val _eventShowDialog = MutableLiveData<Boolean>()
    val eventShowDialog: LiveData<Boolean>
        get() = _eventShowDialog

    var vaisseauName = ""

    init {
        _eventWaitingRoomStatus.value = EventType.WAITING_FOR_PLAYER
    }

    /**
     * Callback called when the ViewModel is destroyed
     */

    override fun onCleared() {
        super.onCleared()
    }

    fun joinRoom(name: String){
        vaisseauName = name
        _eventSocketActive.value = true
        userRepository.setStateWaiting()
        userRepository.currentUser.value?.let { webSocket.joinRoom(name, it) }
    }

    fun onDisplayPopupRoomName() {
        _eventShowDialog.value = true
    }

    fun sendReady(){
        _eventWaitingRoomStatus.value = EventType.READY
        userRepository.setStateReady()
        webSocket.sendReady()
    }

}