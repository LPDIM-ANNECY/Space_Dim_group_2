package fr.test200.spacedim.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.test200.spacedim.MainActivity
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.DashboardFragmentBinding

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

        return binding.root
    }

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