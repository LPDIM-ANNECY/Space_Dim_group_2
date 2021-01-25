package fr.test200.spacedim.waitingRoom

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dataClass.*
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import kotlinx.android.synthetic.main.waiting_room_fragment.*


class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private val viewModel: WaitingRoomViewModel by viewModels {
        WaitingRoomViewModelFactory(SpaceDim.userRepository, SpaceDim.webSocket)
    }

    private var dialog: RegisterDialogFragment? = null

    private var soundAmbiance: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.waiting_room_fragment,
            container,
            false
        )

        binding.waitingRoomViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setTextSpatialshipName(getString(R.string.waitingRoom_no_ship_join))
        //endregion

        //region Observer
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
                binding.txtSocketActive.text = getString(R.string.socket_active)
            }
        })
        //endregion

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        return binding.root
    }

    private fun updateRoomStatus(eventType: State) {
        when(eventType){
            State.WAITING ->{
                binding.buttonReady.visibility = View.VISIBLE
                binding.buttonReady.text = getString(R.string.ready)
                binding.buttonReady.isEnabled = true
            }
            State.READY -> {
                binding.buttonReady.text = Html.fromHtml(getString(R.string.you_are_ready), Html.FROM_HTML_MODE_COMPACT)
                binding.buttonReady.isEnabled = false
            }
            State.IN_GAME -> {

            }
            State.OVER -> {
                binding.buttonJoinRoom.visibility = View.VISIBLE
                viewModel.setTextSpatialshipName(getString(R.string.waitingRoom_no_ship_join))
            }
        }
    }

    private fun updateWebSocketState(event: Event?) {
        when(event){
            is Event.WaitingForPlayer -> {
                //Set interface UI
                binding.socketActiveColor.setImageResource(R.drawable.ic_socket_active)
                viewModel.setTextSpatialshipName(Html.fromHtml(getString(R.string.spatialship_room, viewModel.spatialshipName.value), Html.FROM_HTML_MODE_COMPACT).toString())
                binding.buttonJoinRoom.visibility = View.INVISIBLE
                //Create cardView with user in room
                binding.listPlayerLayout.removeAllViews()
                event.userList.forEach {
                    createCardView(it.name, it.state)
                }
                //Check all player ready
               if (event.userList.count() > 1 && event.allUserReady()) {
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
            dialog = RegisterDialogFragment(getString(R.string.spatialship_name), "", getString(R.string.go), getString(R.string.cancel))
        }

        dialog?.onPositiveClick = {
            if(it.trim().isNotEmpty()) viewModel.joinRoom(it)
        }
        activity?.supportFragmentManager?.let {
            dialog?.show(it, "RegisterDialog")
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