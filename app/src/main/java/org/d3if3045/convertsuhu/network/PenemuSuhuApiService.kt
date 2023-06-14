package org.d3if3045.convertsuhu.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3045.convertsuhu.model.PenemuSuhu
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "INIimammm/api-penemu-suhu/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PenemuSuhuApiService {
    @GET("static-api.json")
    suspend fun getPenemuSuhu(): List<PenemuSuhu>
}

object PenemuSuhuApi {
    val service: PenemuSuhuApiService by lazy {
        retrofit.create(PenemuSuhuApiService::class.java)
    }
    fun getPenemuSuhuUrl(imageId : String): String{
        return "$BASE_URL$imageId.jpg"
    }

    enum class ApiStatus { LOADING, SUCCESS, FAILED }
}
