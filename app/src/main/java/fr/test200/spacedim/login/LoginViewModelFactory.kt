package fr.test200.spacedim.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.test200.spacedim.repository.UserRepository

class LoginViewModelFactory(private val repo: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}