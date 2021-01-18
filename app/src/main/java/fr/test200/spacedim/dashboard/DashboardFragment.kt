package fr.test200.spacedim.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.DashboardFragmentBinding
import fr.test200.spacedim.end.EndFragmentDirections

class DashboardFragment : Fragment() {

    private lateinit var binding: DashboardFragmentBinding

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.dashboard_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        binding.dashboardViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.moduleNumber.observe(viewLifecycleOwner, Observer { newModuleNumber ->
            for (indexModule in 1..newModuleNumber) {
                val button = Button(this.context)
            }
        })

        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer<Boolean> { isFinished ->
            if (isFinished) gameFinishedWin()
        })

        return binding.root
    }


    fun gameFinishedWin() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToEndFragment()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishedComplete()
    }

}