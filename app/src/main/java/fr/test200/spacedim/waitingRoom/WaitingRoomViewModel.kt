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

sealed class HTTPState {
    object Loading : HTTPState()
    data class LoginSuccessful(val user: User) : HTTPState()
    data class Error(val errorMessage: String, val httpException: HttpException? = null) :
        HTTPState()
}

class WaitingRoomViewModel(userRepository: UserRepository) : ViewModel() {

    // userRepository
    val userRepository : UserRepository by lazy {
        userRepository
    }

    private val _eventGoDashBoard = MutableLiveData<Boolean>()
    val eventGoDashBoard: LiveData<Boolean>
        get() = _eventGoDashBoard

    // recupération user list du websocket
    //fun getWebSocketState(): LiveData<Event> = Event.WaitingForPlayer

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
}