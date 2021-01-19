package fr.test200.spacedim.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.test200.spacedim.GameEventTools
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.dataClass.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import kotlin.coroutines.suspendCoroutine

class WSListener : WebSocketListener() {

    // list user
    var webSocketState = MutableLiveData<Event>()

    private var webSocket: WebSocket? = null

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("log", "onOpen")
        println("onOpen")
        println(response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        //Log.i("log", "onMessage")

        when(GameEventTools.spaceEventParser.fromJson(text)!!::class.java) {
            Event.WaitingForPlayer::class.java -> {
                val webSocketState = GameEventTools.spaceEventParser.fromJson(text) as Event.WaitingForPlayer
                updateWebSocketState(webSocketState)
                /*for(user in userList.userList) {
                    Log.i("Name player :", user.name)
                }
                Log.i("Nombre de player", userList.userList.size.toString())*/
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        output("Receiving bytes : " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
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
    private fun updateWebSocketState(event: Event){
        webSocketState.postValue(event)
    }

    fun joinRoom(name: String, user: User){
        val request = Request.Builder().url("${Config.PROTOCOL}://${Config.HOST}:${Config.PORT}/ws/join/${name}/${user.id}").build()
        webSocket = OkHttpClient().newWebSocket(request, this)
    }

}