package fr.test200.spacedim.dataClass

import retrofit2.HttpException

sealed class WebSocketState {
    object Loading : WebSocketState()
    data class WaitingForRoom(val waitingText: String) : WebSocketState()
    data class Error(val errorMessage: String, val httpException: HttpException? = null) :
        WebSocketState()
}