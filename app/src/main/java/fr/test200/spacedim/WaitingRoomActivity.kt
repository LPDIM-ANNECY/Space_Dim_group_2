package fr.test200.spacedim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class WaitingRoomActivity : AppCompatActivity() {

    private val tag = "Waiting activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)
    }
    override fun onStart() {
        super.onStart()
        Log.i(tag,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag,"onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(tag, "restart")
    }
}