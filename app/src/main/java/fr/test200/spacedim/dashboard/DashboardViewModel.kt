package fr.test200.spacedim.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class moduleTypes {
    BUTTON, SWITCH
}

class DashboardViewModel : ViewModel() {

    private val moduleMaxNumber: Int = 9
    private val _moduleNumber = MutableLiveData<Int>()
    val moduleNumber: LiveData<Int>
        get() = _moduleNumber

    //private val moduleList = List(moduleNumber) {}

    init {
        _moduleNumber.value = (4..moduleMaxNumber).random()
        for (moduleIndex in 0..moduleTypes.values().size) {
            val  moduleType = moduleTypes.values()[(0..moduleTypes.values().size).random()]

        }
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}