package com.sam.employeerollcallapplication.gateway

import com.sam.employeerollcallapplication.utils.Constants.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class GateWayNetworkServices {

    val retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        retrofit = builder.client(httpClient).build()
    }

    companion object {
        private var self: GateWayNetworkServices? = null

        val instance: GateWayNetworkServices
            get() {
                if (self == null) {
                    synchronized(GateWayNetworkServices::class.java) {
                        if (self == null) {
                            self =
                                GateWayNetworkServices()
                        }
                    }
                }
                return self!!
            }
    }
}