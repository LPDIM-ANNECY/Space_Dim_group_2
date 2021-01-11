package fr.test200.spacedim.end

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.EndLayoutFragmentBinding


class EndFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: EndLayoutFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.end_layout_fragment, container, false)

        binding.endBtnRetry.setOnClickListener {
            findNavController().navigate(EndFragmentDirections.actionEndFragmentToWaitingRoomFragment())
        }

        return binding.root
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_layout)

        //region win or loose intent param
        val win = intent.getBooleanExtra("winKey", false)

        if (!win)
            end_text_comment.text = getString(R.string.end_text_comment_lose)
        //endregion

        end_btn_retry.setOnClickListener {
            val intent = Intent(this, WaitingRoomFragment::class.java)
            startActivity(intent)
        }

        Log.i(tag, "onCreate")
    }

    override fun onBackPressed() {
        createDialog(
            this,
            "Quitter",
            "Une derniere partie ?",
            true,
            fun() {
                Log.i(tag, "close")
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