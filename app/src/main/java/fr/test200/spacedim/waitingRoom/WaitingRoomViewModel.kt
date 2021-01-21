package fr.test200.spacedim.waitingRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.EventType
import fr.test200.spacedim.network.WSListener
import fr.test200.spacedim.repository.UserRepository

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

    private val _eventWaitingRoomStatus = MutableLiveData<EventType>()
    val eventWaitingRoomStatus: LiveData<EventType>
        get() = _eventWaitingRoomStatus

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
        _eventWaitingRoomStatus.value = EventType.GAME_STARTED
        _eventGoDashBoard.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun joinRoom(name: String){
        vaisseauName = name
        _eventSocketActive.value = true
        _eventWaitingRoomStatus.value = EventType.WAITING_FOR_PLAYER
        userRepository.currentUser.value?.let { webSocket.joinRoom(name, it) }
    }

    fun onReady() {
        _eventWaitingRoomStatus.value = EventType.READY
    }

    fun onDisplayPopupRoomName() {
        _eventWaitingRoomStatus.value = EventType.NOT_IN_ROOM
    }

    fun onSwitchActivity() {
        _eventSwitchActivity.value = true
    }

}