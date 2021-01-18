package fr.test200.spacedim.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.dataClass.UserPost
import fr.test200.spacedim.network.SpaceDimApi
import fr.test200.spacedim.repository.UserRepository

class LoginViewModel(userRepository: UserRepository) : ViewModel() {

    // current User
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    // go to other
    private val _eventGo = MutableLiveData<Boolean>()
    val eventGo: LiveData<Boolean>
        get() = _eventGo

    init {}

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    suspend fun loginUser(userName: String){
        val isLogin = SpaceDimApi.userService.getUserByName(userName)
        if (isLogin.isSuccessful){

        } else {

        }
    }

    suspend fun registerUser(userName: String){
        val isRegister = SpaceDimApi.userService.registerUser(UserPost(userName))
        if (isRegister != null) {
            if (isRegister.isSuccessful){

            } else {

            }
        }
    }

    fun onGo() {
        _eventGo.value = true
    }
    fun onGoComplete() {
        _eventGo.value = false
    }
}