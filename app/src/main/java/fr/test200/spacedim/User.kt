package fr.test200.spacedim

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

data class User(
    val id: Int, val name: String, val avatar: String,
    val score: Int, var state: State = State.OVER
) {

    private val urlBase = "https://spacedim.async-agency.com/api/"
    private val client = OkHttpClient()

    /*
    fun getAllUsers(sort: String? = null): String {
        val request = Request.Builder()
            .addHeader("Content-Type", "Application/json")
            .url(urlBase + "users")
            .build()

    }*/
}