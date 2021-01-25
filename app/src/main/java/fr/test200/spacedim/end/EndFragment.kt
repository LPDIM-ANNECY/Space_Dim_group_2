package fr.test200.spacedim.end

import android.content.Intent
import android.net.Uri
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //region Initialisation Fragment
        val binding: EndFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.end_fragment,
                container,
                false
        )

        viewModelFactory = EndViewModelFactory(EndFragmentArgs.fromBundle(requireArguments()).score, EndFragmentArgs.fromBundle(requireArguments()).win)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EndViewModel::class.java)

        binding.endViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //endregion

        //region Observer
        viewModel.win.observe(viewLifecycleOwner, {
            if (it) viewModel.setWinText(getString(R.string.end_text_comment_win)) else viewModel.setWinText(getString(R.string.end_text_comment_lose))
        })

        viewModel.score.observe(viewLifecycleOwner, {
            when {
                it < 200 -> viewModel.setReputationText(getString(R.string.end_text_space_reputation_laika))
                it in 200..399 -> viewModel.setReputationText(getString(R.string.end_text_space_reputation_yuri))
                it in 400..699 -> viewModel.setReputationText(getString(R.string.end_text_space_reputation_valentina))
                else -> viewModel.setReputationText(getString(R.string.end_text_space_reputation_apollo11))
            }
        })

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(EndFragmentDirections.actionEndFragmentToWaitingRoomFragment())
                viewModel.onPlayAgainComplete()
            }
        })

        viewModel.eventHighScore.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(EndFragmentDirections.actionEndFragmentToHighscoreFragment())
                viewModel.onGoHighScoreComplete()
            }
        })
        //endregion

        return binding.root
    }
}