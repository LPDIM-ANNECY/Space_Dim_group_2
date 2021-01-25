package fr.test200.spacedim.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.*
import fr.test200.spacedim.dataClass.EditTextName
import fr.test200.spacedim.dataClass.HTTPState
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.dataClass.UserPost
import fr.test200.spacedim.network.SpaceDimApi
import fr.test200.spacedim.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(userRepository: UserRepository) : ViewModel() {

    //region user
    val userRepository : UserRepository by lazy {
        userRepository
    }
    val currentUser: LiveData<User> = userRepository.currentUser
    //endregion

    //region EditText
    val editTextName = EditTextName()
    //endregion

    // Etat http d'une requete pour afficher un texte en fonction dans le fragment
    val httpResponse = MediatorLiveData<HTTPState>()

    //region Event
    private val _eventTryConnection = MutableLiveData<Boolean>()
    val eventTryConnection: LiveData<Boolean>
        get() = _eventTryConnection
    //endregion

    init {
        editTextName.name = ""
        _eventTryConnection.value = false

        httpResponse.addSource(userRepository.currentUser) {
            httpResponse.value = HTTPState.LoginSuccessful(it)
        }
    }
    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun loginUser(userName: String) {
        _eventTryConnection.value = true
        httpResponse.value = HTTPState.Loading
        viewModelScope.launch {
            val isLogin = SpaceDimApi.userService.getUserByName(userName)
            isLogin?.let {
                if (it.isSuccessful){
                    userRepository.loginUser(it.body())
                } else {
                    if (it.code() == 400){
                        httpResponse.value = HTTPState.Error("Veuillez remplir le champ")
                    }
                    if (it.code() == 401){
                        httpResponse.value = HTTPState.Error("Utilisateur existe déjà")
                    }
                    if (it.code() == 404){
                        httpResponse.value = HTTPState.Error("Utilisateur n'existe pas")
                    }
                }
            }
        }
    }

    fun registerUser(userName: String) {
        _eventTryConnection.value = true
        viewModelScope.launch {
            val isRegister = SpaceDimApi.userService.registerUser(UserPost(userName))
            isRegister?.let {
                if (it.isSuccessful){
                    userRepository.registerUser(it.body())
                } else {
                    if (it.code() == 400){
                        httpResponse.value = HTTPState.Error("Veuillez remplir le champ")
                    }
                    if (it.code() == 401){
                        httpResponse.value = HTTPState.Error("Utilisateur existe déjà")
                    }
                }
            }
        }
    }

}