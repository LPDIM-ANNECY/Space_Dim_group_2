package fr.test200.spacedim.waitingRoom

import RegisterDialogFragment
import android.app.AlertDialog
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dashboard.DashboardFragmentDirections
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import fr.test200.spacedim.login.LoginFragmentDirections
import fr.test200.spacedim.network.Config
import fr.test200.spacedim.network.WSListener
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private val viewModel: WaitingRoomViewModel by viewModels {
        WaitingRoomViewModelFactory(SpaceDim.userRepository, WSListener())
    }

    private var dialog: AlertDialog? = null

    private var soundAmbiance: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.waiting_room_fragment,
            container,
            false
        )

        binding.waitingRoomViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // OBSERVABLE
        /*viewModel.eventGoDashBoard.observe(viewLifecycleOwner, Observer<Boolean> { isFinished ->
            if (isFinished) changeViewToDashBoard()
        })*/

        viewModel.getWebSocketState().observe(viewLifecycleOwner, Observer {
            updateWebSocketState(it)
        })

        viewModel.eventDisplayPopupRoomName.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) showDialog()
        })

        viewModel.eventIsInRoom.observe(viewLifecycleOwner, Observer<Boolean> { isInRoom ->
            if(isInRoom) {
                binding.buttonJoinRoom.visibility = View.GONE
            }
        })

        viewModel.eventSocketActive.observe(viewLifecycleOwner, Observer<Boolean> { isActive ->
            if(isActive) {
                binding.txtSocketActive.text = "Socket active"
            }
        })

        viewModel.eventSwitchActivity.observe(viewLifecycleOwner, Observer<Boolean> {
            findNavController().navigate(WaitingRoomFragmentDirections.actionWaitingRoomFragmentToDashboardFragment())
        })



        /*
        viewModel.eventValidateRoomName.observe(viewLifecycleOwner, Observer<Boolean> {
            print("test create")
            Toast.makeText(this.activity, viewModel.textRoomName.name, Toast.LENGTH_LONG).show()
            dialog?.dismiss()
        })*/


        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        return binding.root
    }

    private fun updateWebSocketState(event: Event?) {
        when(event){
            is Event.WaitingForPlayer -> {

            }
        }
    }

    fun changeViewToDashBoard() {
        MediaPlayer.create(this.activity, R.raw.decol).start()
        val action = WaitingRoomFragmentDirections.actionWaitingRoomFragmentToDashboardFragment()
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGoDashboardComplete()
    }
    // Usage
    private fun showDialog() {
        val dialog = RegisterDialogFragment("Nom du vaisseau", "", "Go !", "Annuler")
        dialog.onPositiveClick = {
            viewModel.joinRoom(it)
        }
        activity?.supportFragmentManager?.let { dialog.show(it, "RegisterDialog") }

    }
}