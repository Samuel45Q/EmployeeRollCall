package com.mpumi.employeerollcallapplication.gateway

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.mpumi.employeerollcallapplication.BuildConfig
import com.mpumi.employeerollcallapplication.utils.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

object GateWayNetworkServices {

    private val retrofit: Retrofit
        get() = createRetrofit()

    fun <TService : Any?> create(service: Class<TService>): TService = retrofit.create(service)

    fun <TResponse> call(
        service: Observable<Response<TResponse>>,
        response: ServiceResponse<TResponse>
    ) {
        service.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(serviceResponseObserver(response))
    }

    fun <TResponse> call(
        service: Observable<Response<TResponse>>,
        pollingSeconds: Int,
        response: ServiceResponse<TResponse>
    ) {
        var numberOfServiceCalls = 0
        service.repeatWhen { completedServiceCall ->
                completedServiceCall.delay(1, TimeUnit.SECONDS)
            }.filter { tResponse ->
                numberOfServiceCalls++
                tResponse.code() == HttpURLConnection.HTTP_OK || numberOfServiceCalls == pollingSeconds
            }.takeUntil { tResponse ->
                tResponse.code() == HttpURLConnection.HTTP_OK || numberOfServiceCalls == pollingSeconds
            }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(serviceResponseObserver(response))
    }

    fun call(call: Call<JsonObject>, classOfT: Class<*>, response: ServiceResponse<Any>) {
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                response.onUnknownError(t.localizedMessage)
            }

            override fun onResponse(call: Call<JsonObject>, tResponse: Response<JsonObject>) {
                try {
                    when {
                        tResponse.code() == HttpURLConnection.HTTP_OK -> {
                            if (tResponse.body() != null) {
                                val body = Gson().fromJson(tResponse.body()?.asJsonObject, classOfT)
                                response.onSuccess(body)
                            } else {
                                response.onUnknownError("No data returned")
                            }
                        }
                        tResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> response.onUnauthorised(
                            tResponse.message()
                        )
                        else -> response.onNetworkError(tResponse.code(), tResponse.message())
                    }

                } catch (exception: Exception) {
                    Log.e("GateWayNetworkServices", "Error on service response", exception)
                    response.onUnknownError(exception.localizedMessage)
                }
            }
        })
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(buildCustomGsonFactory())
            .baseUrl(Constants.BASE_URL)
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val builder = getOkHttpClientBuilder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
    }

    private fun buildCustomGsonFactory(): Converter.Factory {
        val gson = GsonBuilder()
            .setDateFormat(Constants.DEFAULT_DATE_FORMAT)
            .create()
        return GsonConverterFactory.create(gson)
    }

    private fun <TResponse> serviceResponseObserver(response: ServiceResponse<TResponse>) = object :
        Observer<Response<TResponse>> {
        override fun onSubscribe(d: Disposable) = Unit

        override fun onComplete() = Unit

        override fun onError(error: Throwable) {
            response.onUnknownError(error.localizedMessage)
        }

        override fun onNext(tResponse: Response<TResponse>) {
            when {
                tResponse.code() == HttpURLConnection.HTTP_OK -> response.onSuccess(tResponse.body())
                tResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> response.onUnauthorised(
                    tResponse.message()
                )
                else -> response.onNetworkError(tResponse.code(), tResponse.message())
            }
        }
    }

}