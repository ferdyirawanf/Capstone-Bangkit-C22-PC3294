package com.example.fruitdetectionnews

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseUrl {
    const val BASE_URL = "https://us-central1-global-walker-343906.cloudfunctions.net"
    const val BASE_URL2 = "https://newsapi.org/v2/"
}

class ServicesGenerator {
    companion object{
        fun apiService1(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun apiService2(): ApiService2{
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService2::class.java)
        }
    }
    /*private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://us-central1-global-walker-343906.cloudfunctions.net")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()*/


   /* fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
*/

}