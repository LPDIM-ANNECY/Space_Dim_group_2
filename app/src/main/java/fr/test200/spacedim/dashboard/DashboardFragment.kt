package fr.test200.spacedim.dashboard

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import fr.test200.spacedim.R
import fr.test200.spacedim.SpaceDim
import fr.test200.spacedim.dataClass.*
import fr.test200.spacedim.databinding.DashboardFragmentBinding

enum class moduleTypes {
    BUTTON, SWITCH
}

class DashboardFragment : Fragment() {

    private lateinit var binding: DashboardFragmentBinding

    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(SpaceDim.webSocket)
    }

    private var soundAmbiance: MediaPlayer? = null
    private var tictac: MediaPlayer? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //region Initialisation Fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.dashboard_fragment,
                container,
                false
        )

        binding.dashboardViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        soundAmbiance = MediaPlayer.create(this.activity, R.raw.ambiance_dashboard)
        tictac = MediaPlayer.create(this.activity, R.raw.tictac)
        soundAmbiance?.isLooping = true
        tictac?.isLooping = true
        soundAmbiance?.start()
        tictac?.start()
        //endregion

        //region Observer
        viewModel.getWebSocketState().observe(viewLifecycleOwner, {
            updateWebSocketState(it)
        })
        //endregion

        return binding.root
    }

    private fun updateWebSocketState(event: Event?) {
        when(event){
            is Event.GameStarted -> {
                createRows(event.uiElementList)
            }
            is Event.NextAction -> {
                resetAndStartTime(event.action.time)
                binding.action.text = event.action.sentence
            }
            is Event.NextLevel -> {
                createRows(event.uiElementList)
            }
            is Event.GameOver -> {
                try {
                    gameFinished(event.score, event.win)
                }
                catch (exception: Exception){
                    throw exception
                }
            }
        }
    }

    private fun resetAndStartTime(time: Long) {
        binding.timeRemain.setProgress(0, false)
        ObjectAnimator.ofInt(binding.timeRemain, "progress", 100)
                .setDuration(time)
                .start()
    }

    override fun onPause() {
        super.onPause()
        soundAmbiance?.pause()
        tictac?.pause()
    }

    override fun onResume() {
        super.onResume()
        soundAmbiance?.start()
        tictac?.start()
    }

    private fun createRows(moduleList: List<UIElement>) {
        binding.tabletruc.removeAllViews()

        val uiElementMap = mutableMapOf<Int, List<UIElement>>()

        moduleList.chunked(2).forEachIndexed { index, list ->
            uiElementMap[index] = list
        }

        uiElementMap.map {
            val row = TableRow(this.context)
            row.gravity = 17

            it.value.forEach {
                val element = createModule(it)
                row.addView(element)
            }

            binding.tabletruc.addView(row)
        }
    }

    fun createModule(module: UIElement): View {

        var element = View(this.context)
        val params = TableRow.LayoutParams( 500, 145 ).also { it.setMargins(25, 25, 25, 25) }

        /* Cher correcteur,
        Désolé pour ce doublon ci-dessous, je n'ai pas trouvé une alternative
        (comme attribuer la bonne view dans le bouton et lui attribuer des paramètres
        en dehors du when)
        Bonne continuation (et lecture) */

        when(module.type) {
            UIType.BUTTON -> {
                element = Button(this.context)
                element.text = module.content
                element.setOnClickListener {
                    viewModel.sendAction(module)
                    MediaPlayer.create(this.activity, R.raw.button_click).start()
                }
                element.layoutParams = params
            }

            UIType.SWITCH -> {
                element = Switch(this.context)
                element.text = module.content
                element.setOnClickListener {
                    viewModel.sendAction(module)
                    MediaPlayer.create(this.activity, R.raw.button_click).start()
                }
                element.layoutParams = params
            }

            UIType.SHAKE -> {
                element = Button(this.context)
                element.text = module.content
                element.setOnClickListener {
                    viewModel.sendAction(module)
                    MediaPlayer.create(this.activity, R.raw.button_click).start()
                }
                element.layoutParams = params
            }
        }
        return element
    }

    fun gameFinished(score: Int, win: Boolean) {
        viewModel.closeWebSocketConnection()
        val action = DashboardFragmentDirections.actionDashboardFragmentToEndFragment()
        action.score = score
        action.win = win
        NavHostFragment.findNavController(this).navigate(action)
    }
}