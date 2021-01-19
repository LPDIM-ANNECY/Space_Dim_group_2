package fr.test200.spacedim.waitingRoom

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dashboard.DashboardFragmentDirections
import fr.test200.spacedim.dataClass.Event
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding
import fr.test200.spacedim.network.Config
import fr.test200.spacedim.network.WSListener
import kotlinx.android.synthetic.main.waiting_room_fragment.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private val viewModel: WaitingRoomViewModel by viewModels{
        WaitingRoomViewModelFactory(SpaceDim.userRepository, WSListener())
    }

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



        binding.buttonJoinRoom.setOnClickListener {         val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
            val request = Request.Builder()
                .url("${Config.PROTOCOL}://${Config.HOST}:${Config.PORT}/ws/join/testNathan/101")
                .build()

            val listener = WSListener()
            val ws = client.newWebSocket(request, listener) }


        // OBSERVABLE
        /*viewModel.eventGoDashBoard.observe(viewLifecycleOwner, Observer<Boolean> { isFinished ->
            if (isFinished) changeViewToDashBoard()
        })*/

        viewModel.getWebSocketState().observe(viewLifecycleOwner, Observer {
            updateWebSocketState(it)
        })

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

}