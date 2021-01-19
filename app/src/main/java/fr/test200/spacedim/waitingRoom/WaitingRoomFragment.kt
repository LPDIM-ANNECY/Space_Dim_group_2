package fr.test200.spacedim.waitingRoom

import fr.test200.spacedim.dataClass.RegisterDialogFragment
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
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
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import fr.test200.spacedim.network.WSListener
import java.util.concurrent.TimeUnit

class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private val viewModel: WaitingRoomViewModel by viewModels {
        WaitingRoomViewModelFactory(SpaceDim.userRepository, WSListener())
    }

    private var dialog: RegisterDialogFragment? = null

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
            if (it) {
                showDialog()
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
                binding.vaisseauName.text = Html.fromHtml("Vaisseau : <b>${viewModel.vaisseauName}</b>", Html.FROM_HTML_MODE_COMPACT)
                binding.buttonJoinRoom.visibility = View.GONE
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
        if(dialog === null) {
            dialog = RegisterDialogFragment("Nom du vaisseau", "", "Go !", "Annuler")
        }

        dialog?.onPositiveClick = {
            viewModel.joinRoom(it)
        }
        activity?.supportFragmentManager?.let {
            dialog?.show(it, "RegisterDialog")
            println("isHidden " + dialog?.isHidden)
        }
    }
}