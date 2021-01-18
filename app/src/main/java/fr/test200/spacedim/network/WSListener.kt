package fr.test200.spacedim.network

import android.util.Log
import fr.test200.spacedim.GameEventTools
import fr.test200.spacedim.dataClass.Event
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WSListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.i("log", "onOpen")
        println("onOpen")
        println(response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        //Log.i("log", "onMessage")

        when(GameEventTools.spaceEventParser.fromJson(text)!!::class.java) {
            Event.WaitingForPlayer::class.java -> {
                val userList = GameEventTools.spaceEventParser.fromJson(text) as Event.WaitingForPlayer

                for(user in userList.userList) {
                    Log.i("Name player :", user.name)
                }
                Log.i("Nombre de player", userList.userList.size.toString())
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

}