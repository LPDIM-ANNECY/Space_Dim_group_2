package fr.test200.spacedim.dashboard

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
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
    }

    fun onSkip() {
        _moduleNumber.value = (4..moduleMaxNumber).random()
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}