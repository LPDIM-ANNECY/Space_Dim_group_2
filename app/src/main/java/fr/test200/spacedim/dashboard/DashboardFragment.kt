package fr.test200.spacedim.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fr.test200.spacedim.end.EndFragment
import fr.test200.spacedim.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardFragment : AppCompatActivity() {
    private val tag = "Dashboard page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        winButton.setOnClickListener {
            val intent = Intent(this, EndFragment::class.java)
            intent.putExtra("winKey",true)
            startActivity(intent)
        }

        looseButton.setOnClickListener {
            val intent = Intent(this, EndFragment::class.java)
            intent.putExtra("winKey",false)
            startActivity(intent)
        }

        Log.i(tag, "onCreate")
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