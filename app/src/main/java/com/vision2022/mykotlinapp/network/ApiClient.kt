package com.vision2022.mykotlinapp.network
import android.content.Context
import com.google.gson.GsonBuilder
import com.vision2022.mykotlinapp.util.MyApplication
import com.vision2022.mykotlinapp.util.SharedPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.AccessController.getContext
import java.util.concurrent.TimeUnit

object ApiClient {
    var BASE_URL:String="http://192.168.8.102:8080/"

    val getClient: ApiInterface
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))

                    .client(OkHttpClient.Builder().addInterceptor { chain ->

                        val context: Context = MyApplication.instance
                        val sharedPreference:SharedPreference=SharedPreference(context)

                        var jwttoken = sharedPreference.getValueString("token");

                        if (jwttoken == null) {
                            jwttoken = ""
                        }

                        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${jwttoken}").build()

                        chain.proceed(request)
                    }.build())

                .build()
            return retrofit.create(ApiInterface::class.java)
        }
}
