package fr.test200.spacedim.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.test200.spacedim.network.WSListener


class DashboardViewModelFactory(private val webSocket: WSListener) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(webSocket) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}