package fr.test200.spacedim.waitingRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.network.WSListener
import fr.test200.spacedim.repository.UserRepository
import retrofit2.HttpException

class WaitingRoomViewModel(userRepository: UserRepository, webSocket: WSListener) : ViewModel() {

    // userRepository
    val userRepository : UserRepository by lazy {
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
        userRepository.currentUser.value?.let { webSocket.joinRoom(name, it) }
    }
}