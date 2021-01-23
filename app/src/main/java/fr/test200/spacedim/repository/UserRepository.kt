package fr.test200.spacedim.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.State
import fr.test200.spacedim.dataClass.User

class UserRepository {
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    var currentUserState = MutableLiveData(State.OVER)

    fun loginUser(user: User?){
        _currentUser.postValue(user)
    }

    fun registerUser(user: User?){
        _currentUser.postValue(user)
    }

    fun setStateReady(){
        currentUserState.postValue(State.READY)
    }

    fun setStateWaiting(){
        currentUserState.postValue(State.WAITING)
    }

    fun setStateInGame(){
        currentUserState.postValue(State.IN_GAME)
    }
}