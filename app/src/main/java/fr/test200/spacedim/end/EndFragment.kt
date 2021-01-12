package fr.test200.spacedim.end

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.EndFragmentBinding


class EndFragment : Fragment() {

    private lateinit var viewModel: EndViewModel
    private lateinit var viewModelFactory: EndViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: EndFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.end_fragment,
                container,
                false
        )

        viewModelFactory = EndViewModelFactory(EndFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EndViewModel::class.java)
        // Data binding
        binding.endViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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
}