package fr.test200.spacedim.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.*
import fr.test200.spacedim.dataClass.EditTextName
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.dataClass.UserPost
import fr.test200.spacedim.network.SpaceDimApi
import fr.test200.spacedim.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class HTTPState {
    object Loading : HTTPState()
    data class LoginSuccessful(val user: User) : HTTPState()
    data class Error(val errorMessage: String, val httpException: HttpException? = null) :
        HTTPState()
}

class LoginViewModel(userRepository: UserRepository) : ViewModel() {

    // userRepository
    val userRepository : UserRepository by lazy {
        userRepository
    }
    // current User
    val currentUser: LiveData<User> = userRepository.currentUser

    // edit text value
    val editTextName = EditTextName()

    // etat http d'une requete pour afficher un texte en fonction dans le fragment
    val httpResponse = MediatorLiveData<HTTPState>()


    init {
        editTextName.name = ""

        //
        httpResponse.addSource(userRepository.currentUser){
            httpResponse.value = HTTPState.LoginSuccessful(it)
        }
    }
    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun loginUser(userName: String){
        httpResponse.value = HTTPState.Loading
        viewModelScope.launch {
            val isLogin = SpaceDimApi.userService.getUserByName(userName)
            isLogin?.let {
                if (it.isSuccessful){
                    userRepository.loginUser(it.body())
                } else {
                    if (it.code() == 400){
                        httpResponse.value = HTTPState.Error("Edit text is null")
                    }
                }
            }
        }
    }

    fun registerUser(userName: String){
        viewModelScope.launch {
            val isRegister = SpaceDimApi.userService.registerUser(UserPost(userName))
            isRegister?.let {
                if (it.isSuccessful){
                    userRepository.registerUser(it.body())
                } else {
                    throw HttpException(it)
                }
            }
        }
    }

}