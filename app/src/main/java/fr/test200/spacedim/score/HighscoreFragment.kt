package fr.test200.spacedim.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.test200.spacedim.R
import fr.test200.spacedim.dataClass.User
import fr.test200.spacedim.databinding.HighscoreFragmentBinding
import fr.test200.spacedim.end.EndViewModel

class HighscoreFragment : Fragment() {

    private lateinit var binding: HighscoreFragmentBinding

    private lateinit var viewModel: HighscoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.highscore_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(this).get(HighscoreViewModel::class.java)

        binding.highscoreViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getUserHighscore()
        //endregion

        //region Observer
        viewModel.highscoreUser.observe(viewLifecycleOwner, {
            createCardPlayer(it)
        })
        //endregion

        // event back pressed
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }

        return binding.root
    }

    private fun onBackPressed() {
        findNavController().navigate(HighscoreFragmentDirections.actionHighscoreFragmentToEndFragment())
    }

    private fun createCardPlayer(userList: List<User>) {
        val inflater = LayoutInflater.from(this.context)

        userList.forEach {
            val userCard = inflater.inflate(R.layout.user_card,null) as CardView

            userCard.findViewById<TextView>(R.id.user_name).text = it.name
            userCard.findViewById<TextView>(R.id.user_status).text = it.score.toString()

            userCard.cardElevation = 10F
            val params = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
            params.setMargins(20, 10, 20, 30)
            userCard.layoutParams = params
            binding.listPlayerHighscore.addView(userCard)
        }
    }
}