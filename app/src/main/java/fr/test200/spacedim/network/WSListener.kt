package fr.test200.spacedim.network

import android.util.Log
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.test200.spacedim.GameEventTools
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.State
import fr.test200.spacedim.dataClass.UIElement
import fr.test200.spacedim.dataClass.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

class WSListener : WebSocketListener() {

    var webSocketState = MutableLiveData<Event>()

    var webSocket: WebSocket? = null

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("log", "onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val response = GameEventTools.spaceEventParser.fromJson(text)
        response?.let {
            Log.i("shake", it.toString())
            updateWebSocketState(it)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        output("Receiving bytes : " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        this.webSocket = null
        webSocketState = MutableLiveData<Event>()
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        output("Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    private fun output(txt: String) {
        Log.v("WS", txt)
    }

    // update etat du web socket
    private fun updateWebSocketState(event: Event) {
        webSocketState.postValue(event)
    }

    fun joinRoom(name: String, user: User) {
        val request = Request.Builder().url("${Config.PROTOCOL}://${Config.HOST}:${Config.PORT}/ws/join/${name}/${user.id}").build()
        webSocket = OkHttpClient().newWebSocket(request, this)
    }

    fun sendReady() {
        webSocket?.send("{\"type\":\"READY\", \"value\":true}")
    }

    fun sendAction(uiElement: UIElement) {
        val request = Event.PlayerAction(uiElement)
        webSocket?.send(GameEventTools.spaceEventParser.toJson(request))
    }

}