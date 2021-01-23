package br.com.israelermel.topranked.di.modules.infra

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val infraModule = module {
    // Retrofit

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build()
                chain.proceed(request)
            }
            .followRedirects(false)
            .followSslRedirects(false)
            .retryOnConnectionFailure(false)
            .build()


    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                )
            )
            .build()
    }

}