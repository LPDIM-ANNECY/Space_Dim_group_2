package fr.test200.spacedim.score

import androidx.lifecycle.*
import fr.test200.spacedim.dataClass.HTTPState
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.network.SpaceDimApi
import kotlinx.coroutines.launch


class HighscoreViewModel: ViewModel() {

    val httpResponse = MediatorLiveData<HTTPState>()

    private val _highscoreUser = MutableLiveData<List<User>>()
    val highscoreUser: LiveData<List<User>>
        get() = _highscoreUser

    fun getUserHighscore(){
        httpResponse.value = HTTPState.Loading
        viewModelScope.launch {
            val isLogin = SpaceDimApi.userService.getUsersList("TOP")
            isLogin?.let {
                if (it.isSuccessful){
                    _highscoreUser.value = it.body()
                } else {

                }
            }
        }
    }

}