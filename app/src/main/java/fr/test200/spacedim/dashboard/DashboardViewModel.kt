package fr.test200.spacedim.dashboard

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.UIElement
import fr.test200.spacedim.network.WSListener

class DashboardViewModel(webSocket: WSListener) : ViewModel() {

    //region WebSocket
    val webSocket: WSListener by lazy {
        webSocket
    }
    fun getWebSocketState(): LiveData<Event> = webSocket.webSocketState
    //endregion

    //region Data
    private val _moduleTypeList = MutableLiveData<MutableList<String>>()
    val moduleTypeList: LiveData<MutableList<String>>
        get() = _moduleTypeList
    //endregion

    //region Event
    private val _eventShake = MutableLiveData<Boolean>()
    val eventShake: LiveData<Boolean>
        get() = _eventShake
    //endregion

    init {
        _eventShake.value = false
    }


    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }

    fun sendAction(uiElement: UIElement) {
        webSocket.sendAction(uiElement)
    }

    fun closeWebSocketConnection() {
        webSocket.webSocket?.let { webSocket.onClosing(it, 1000, "finish") }
        webSocket.webSocket = null
    }

    fun onEventShake() {
        _eventShake.value = true
    }

    fun onEventShakeComplete() {
        _eventShake.value = false
    }
}