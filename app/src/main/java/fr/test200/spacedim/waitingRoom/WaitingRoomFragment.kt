package fr.test200.spacedim.waitingRoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.WaitingRoomFragmentBinding

class WaitingRoomFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: WaitingRoomFragmentBinding = DataBindingUtil.inflate(
           inflater, R.layout.waiting_room_fragment, container, false)

        binding.buttonJoinRoom.setOnClickListener {
            findNavController().navigate(WaitingRoomFragmentDirections.actionWaitingRoomFragmentToDashboardFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag,"onDestroy")
    }
}