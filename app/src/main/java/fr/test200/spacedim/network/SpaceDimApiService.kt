package fr.test200.spacedim.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.test200.spacedim.dataClass.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://spacedim.async-agency.com/doc/api/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface SpaceDimApiService {

    @GET("users")
    suspend fun getUsersList(): Response<User>

    @GET("user/find/{name}")
    suspend fun getUserByName(@Path("name") username: String): Response<List<User>>

    // TODO change user in body
    @Headers("Content-Type:application/json")
    @POST("user/register")
    suspend fun registerUser(@Body user: User) : Response<User>?

}

object SpaceDimApi {
    val retrofitService : SpaceDimApiService by lazy {
        retrofit.create(SpaceDimApiService::class.java) }
}