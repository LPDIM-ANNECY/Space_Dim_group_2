package fr.test200.spacedim

import android.app.Application
import fr.test200.spacedim.network.WSListener
import fr.test200.spacedim.repository.UserRepository

class SpaceDim : Application() {

    companion object{
        val userRepository = UserRepository()
        val webSocket = WSListener()
    }

    override fun onCreate() {
        super.onCreate()
    }
}