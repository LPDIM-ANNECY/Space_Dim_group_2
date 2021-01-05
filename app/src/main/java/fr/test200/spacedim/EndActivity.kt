package fr.test200.spacedim

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.test200.spacedim.Utils.Companion.createDialog
import kotlinx.android.synthetic.main.activity_end_layout.*


class EndActivity : AppCompatActivity() {
    private val tag = "End page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_layout)

        //region win or loose intent param
        val win = intent.getBooleanExtra("winKey", false)

        if (!win)
            end_text_comment.text = getString(R.string.end_text_comment_lose)
        //endregion

        end_btn_retry.setOnClickListener {
            val intent = Intent(this, WaitingRoomActivity::class.java)
            startActivity(intent)
        }

        Log.i(tag, "onCreate")
    }

    override fun onBackPressed() {
        createDialog(
            this,
            "Quitter",
            "Une derniere partie ?",
            true,
            fun() {
                Log.i(tag, "close")
            }
        )
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(tag, "restart")
    }
}