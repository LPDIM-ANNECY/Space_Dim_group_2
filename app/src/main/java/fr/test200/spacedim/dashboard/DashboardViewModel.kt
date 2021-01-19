package fr.test200.spacedim.dashboard

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val scoreTest: Int = 42

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _moduleTypeList = MutableLiveData<MutableList<String>>()
    val moduleTypeList: LiveData<MutableList<String>>
        get() = _moduleTypeList

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    init {
        _score.value = scoreTest
    }

    fun onGameFinish() {
        _eventGameFinished.value = true
    }

    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

    /**
     * Callback called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
    }
}