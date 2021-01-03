package fr.test200.spacedim

import android.os.Looper
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


data class UserPost(val name: String) {

    private val urlBase = "https://spacedim.async-agency.com/api/user/register"
    private val client = OkHttpClient()

    private val json = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun create(onSuccess: () -> Unit, onUnauthorized : () -> Unit, onFail : () -> Unit) {
        val body: RequestBody = "{\"name\":\"$name\"}".toRequestBody(json)

        val request = Request.Builder()
            .url(urlBase)
            .post(body)
            .build()

        return client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFail()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    Looper.prepare()
                    if (!response.isSuccessful)
                        onUnauthorized()
                    else
                        onSuccess()
                    Looper.loop()
                }
            }

        })

    }

}