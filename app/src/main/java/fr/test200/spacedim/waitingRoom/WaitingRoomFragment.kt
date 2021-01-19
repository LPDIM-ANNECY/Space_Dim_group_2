package fr.test200.spacedim.waitingRoom

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.dashboard.DashboardFragmentDirections
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding

class WaitingRoomFragment : Fragment() {

    private lateinit var binding: WaitingRoomFragmentBinding

    private lateinit var viewModel: WaitingRoomViewModel

    private var soundAmbiance: MediaPlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.waiting_room_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(this).get(WaitingRoomViewModel::class.java)

        binding.waitingRoomViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventGoDashBoard.observe(viewLifecycleOwner, Observer<Boolean> { isFinished ->
            if (isFinished) changeViewToDashBoard()
        })

        return binding.root
    }

    fun changeViewToDashBoard() {
        MediaPlayer.create(this.activity, R.raw.decol).start()
        val action = WaitingRoomFragmentDirections.actionWaitingRoomFragmentToDashboardFragment()
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGoDashboardComplete()
    }
}