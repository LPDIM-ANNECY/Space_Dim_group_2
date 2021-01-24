package fr.test200.spacedim.waitingRoom

import android.annotation.SuppressLint
import android.app.ActionBar
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dataClass.*
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import fr.test200.spacedim.network.WSListener


class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private val viewModel: WaitingRoomViewModel by viewModels {
        WaitingRoomViewModelFactory(SpaceDim.userRepository, SpaceDim.webSocket)
    }

    private var dialog: RegisterDialogFragment? = null

    private var soundAmbiance: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.waiting_room_fragment,
            container,
            false
        )

        binding.waitingRoomViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // OBSERVABLE
        viewModel.getWebSocketState().observe(viewLifecycleOwner, {
            updateWebSocketState(it)
        })

        viewModel.getUserState().observe(viewLifecycleOwner, {
            updateRoomStatus(it)
        })

        viewModel.eventShowDialog.observe(viewLifecycleOwner, {
            showDialog()
        })

        viewModel.eventSocketActive.observe(viewLifecycleOwner, { isActive ->
            if(isActive) {
                binding.txtSocketActive.text = "Socket active"
            }
        })

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        return binding.root
    }

    private fun updateRoomStatus(eventType: State){
        when(eventType){
            State.WAITING ->{
                binding.buttonReady.visibility = View.VISIBLE
                binding.buttonReady.text = "Ready ?"
                binding.buttonReady.isEnabled = true
            }
            State.READY -> {
                binding.buttonReady.text = Html.fromHtml("<i>You are ready !<br>Waiting for Players to ready up</i>", Html.FROM_HTML_MODE_COMPACT)
                binding.buttonReady.isEnabled = false
            }
            State.IN_GAME -> {

            }
            State.OVER -> {
                binding.buttonJoinRoom.visibility = View.VISIBLE
            }
        }
    }

    private fun updateWebSocketState(event: Event?) {
        when(event){
            is Event.WaitingForPlayer -> {
                binding.socketActiveColor.setImageResource(R.drawable.ic_socket_active)
                //Create Element in fragment
                binding.vaisseauName.text = Html.fromHtml("Vaisseau : <b>${viewModel.vaisseauName}</b>", Html.FROM_HTML_MODE_COMPACT)
                binding.buttonJoinRoom.visibility = View.INVISIBLE
                binding.listPlayerLayout.removeAllViews()
                event.userList.forEach {
                    createCardView(it.name, it.state)
                }
                //Check all player ready
               if (event.userList.count() > 1 && event.allUserReady()){
                   changeViewToDashBoard()
               }
            }
        }
    }

    private fun changeViewToDashBoard() {
        MediaPlayer.create(this.activity, R.raw.decol).start()
        val action = WaitingRoomFragmentDirections.actionWaitingRoomFragmentToDashboardFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun showDialog() {
        if(dialog === null) {
            dialog = RegisterDialogFragment("Nom du vaisseau", "", "Go !", "Annuler")
        }

        dialog?.onPositiveClick = {
            if(it.trim().isNotEmpty()) viewModel.joinRoom(it)
        }
        activity?.supportFragmentManager?.let {
            dialog?.show(it, "RegisterDialog")
            println("isHidden " + dialog?.isHidden)
        }
    }

    @SuppressLint("InflateParams")
    private fun createCardView(name: String, state: State){
        val inflater = LayoutInflater.from(this.context)
        val userCard = inflater.inflate(R.layout.user_card,null) as CardView

        userCard.findViewById<TextView>(R.id.user_name).text = name
        userCard.findViewById<TextView>(R.id.user_status).text = state.toString()

        userCard.cardElevation = 10F
        val params = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
        params.setMargins(20, 10, 20, 30)
        userCard.layoutParams = params
        binding.listPlayerLayout.addView(userCard)
    }

}