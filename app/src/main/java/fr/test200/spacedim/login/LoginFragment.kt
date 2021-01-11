package fr.test200.spacedim.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.LoginFragmentBinding
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import fr.test200.spacedim.waitingRoom.WaitingRoomViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: LoginFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container, false)

        binding.btnLunch.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWaitingRoomFragment())
        }

        return binding.root
    }

    /*private fun createEventRegisterUser(btnRegister: Button) {
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
    }*/

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
}

