package fr.test200.spacedim.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import fr.test200.spacedim.R
import fr.test200.spacedim.UserPost
import fr.test200.spacedim.Utils
import fr.test200.spacedim.waitingRoom.WaitingRoomFragment
import kotlinx.android.synthetic.main.activity_login.*


class LoginFragment : AppCompatActivity() {

    private val tag = "Login page"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_lunch.setOnClickListener {
            val intent = Intent(this, WaitingRoomFragment::class.java)
            startActivity(intent)
        }

        createEventRegisterUser(btn_register)

        Log.i(tag, "onCreate")
    }

    private fun createEventRegisterUser(btnRegister: Button) {
        val oldColors: ColorStateList = login_info_register.textColors // switch white (loading) to red (error)

        btnRegister.setOnClickListener {
            login_info_register.setTextColor(oldColors) // white
            login_info_register.text = resources.getString(R.string.common_loading)
            val editTextPseudo: EditText = findViewById(R.id.login_pseudo)
            val pseudo: String = editTextPseudo.text.trim().toString()

            Utils.hideKeyboard(this)

            UserPost(pseudo).create(
                fun() { // success
                    runOnUiThread { // android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                        login_info_register.text = resources.getString(R.string.login_title_user_create)
                    }
                }, fun() { // unauthorized
                    runOnUiThread {
                        login_info_register.setTextColor(resources.getColor(R.color.text_error, theme))
                        login_info_register.text = resources.getString(R.string.login_title_already_exist)
                    }
                }, fun() { // error
                    runOnUiThread {
                        login_info_register.setTextColor(resources.getColor(R.color.text_error, theme))
                        login_info_register.text = resources.getString(R.string.login_title_fail)
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
                fun() {
                    finish()
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

