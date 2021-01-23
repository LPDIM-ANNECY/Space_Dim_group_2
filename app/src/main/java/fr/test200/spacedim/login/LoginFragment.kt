package fr.test200.spacedim.login

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.Utils.Companion.createDialog
import fr.test200.spacedim.Utils.Companion.hideKeyboard
import fr.test200.spacedim.Utils.Companion.setTimeout
import fr.test200.spacedim.dataClass.HTTPState
import fr.test200.spacedim.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    private val viewModel: LoginViewModel by viewModels{
        LoginViewModelFactory(SpaceDim.userRepository)
    }
    private var soundAmbiance: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )

        binding.loginViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        soundAmbiance = MediaPlayer.create(this.activity, R.raw.space_ambience)
        soundAmbiance?.isLooping = true

        setTimeout({ soundAmbiance?.start() }, 1000)

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        viewModel.eventTryConnection.observe(viewLifecycleOwner, Observer<Boolean>{
            if(it) hideKeyboard(requireActivity())
        })

        viewModel.httpResponse.observe(viewLifecycleOwner, Observer {
            httpStateUi(it)
        })

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        soundAmbiance?.reset()
    }

    override fun onResume() {
        super.onResume()
        soundAmbiance?.start()
    }

    private fun onBackPressed() {
        createDialog(
            this.requireActivity(),
            resources.getString(R.string.login_title_leave),
            resources.getString(R.string.login_message_leave),
            true,
            fun() {
                activity?.finish()
            }
        )
    }

    private fun httpStateUi(state: HTTPState){
        when(state){
            is HTTPState.Loading ->{

            }
            is HTTPState.LoginSuccessful -> {
                soundAmbiance?.stop()
                MediaPlayer.create(this.activity, R.raw.space_validated).start()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWaitingRoomFragment())
            }
            is HTTPState.Error -> {
                binding.loginInfoRegister.setTextColor(resources.getColor(R.color.text_error,
                    this.activity?.theme
                ))
                binding.loginInfoRegister.text = state.errorMessage
                MediaPlayer.create(this.activity, R.raw.space_error).start()
            }
        }
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
    */


}


