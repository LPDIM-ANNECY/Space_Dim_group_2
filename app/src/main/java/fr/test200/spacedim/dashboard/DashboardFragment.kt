package fr.test200.spacedim.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TableRow
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.databinding.DashboardFragmentBinding
import fr.test200.spacedim.end.EndFragmentDirections
import kotlinx.android.synthetic.main.dashboard_fragment.*

enum class moduleTypes {
    BUTTON, SWITCH
}

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

        val moduleTypeList = mutableListOf<String>()
        val moduleMaxNumber = 9;
        val moduleNumber = (4..moduleMaxNumber).random()

        for (moduleIndex in 0..moduleNumber) {
            val moduleType = moduleTypes.values()[(moduleTypes.values().indices).random()]
            moduleTypeList.add(moduleType.toString())
        }

        //création des boutons
        makeList(moduleTypeList)

        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer<Boolean> { isFinished ->
            if (isFinished) gameFinishedWin()
        })

        return binding.root
    }

    private fun makeList(moduleTypeList: MutableList<String>) {
        val numberOfRow: Int = (moduleTypeList.size / 2)
        var countButton = 0
        for (rowIndex in 0..numberOfRow) {
            val row = TableRow(this.context)
            for ((index, type) in moduleTypeList.withIndex()) {
                if (countButton < moduleTypeList.size && (index == rowIndex || index == rowIndex + 1)) {
                    val element: Button = Button(this.context)
                    /*when (type) {
                            "SWITCH" -> {
                                element = Button(this.context)
                            }
                            "BUTTON" -> {
                                element = Switch(this.context)
                            }
                            else -> {
                                print("don't find type")
                            }
                        }*/

                    element.text = "tric"
                    row.addView(element)
                    countButton ++
                }
            }

            binding.tabletruc.addView(row)
        }
    }


    fun gameFinishedWin() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToEndFragment()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishedComplete()
    }
}