package fr.test200.spacedim.dataClass

import retrofit2.HttpException

sealed class HTTPState {
    object Loading : HTTPState()
    data class LoginSuccessful(val user: User) : HTTPState()
    data class Error(val errorMessage: String, val httpException: HttpException? = null) :
        HTTPState()
}