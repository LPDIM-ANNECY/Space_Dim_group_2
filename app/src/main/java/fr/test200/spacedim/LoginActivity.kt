package fr.test200.spacedim

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    private val tag = "Login page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLunch = findViewById<Button>(R.id.btn_lunch)
        btnLunch.setOnClickListener {
            val intent = Intent(this, WaitingRoomActivity::class.java)
            startActivity(intent)
        }

        val btnRegister: Button = findViewById(R.id.btn_register)

        createEventRegisterUser(btnRegister)

        Log.i(tag, "onCreate")
    }

    private fun createEventRegisterUser(btnRegister: Button) {
        val infoRegister: TextView = findViewById(R.id.login_info_register)
        val oldColors: ColorStateList = infoRegister.textColors // switch white (loading) to red (error)

        btnRegister.setOnClickListener {
            infoRegister.setTextColor(oldColors); // white
            infoRegister.text = resources.getString(R.string.common_loading)
            val editTextPseudo: EditText = findViewById(R.id.login_pseudo)
            val pseudo: String = editTextPseudo.text.trim().toString()

            Utils.hideKeyboard(this)

            UserPost(pseudo).create(
                fun() { // success
                    runOnUiThread { // android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                        infoRegister.text = resources.getString(R.string.login_title_user_create)
                    }
                }, fun() { // unauthorized
                    runOnUiThread {
                        infoRegister.setTextColor(resources.getColor(R.color.text_error, theme))
                        infoRegister.text = resources.getString(R.string.login_title_already_exist)
                    }
                }, fun() { // error
                    runOnUiThread {
                        infoRegister.setTextColor(resources.getColor(R.color.text_error, theme))
                        infoRegister.text = resources.getString(R.string.login_title_fail)
                    }
                })
        }
    }

    override fun onBackPressed() {
        Utils.createDialog(
            this,
            resources.getString(R.string.login_title_leave),
            resources.getString(R.string.login_message_leave),
            true,
            fun () {
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

