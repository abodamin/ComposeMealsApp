package com.abdullah.composeapp.data.di

import android.content.Context
import android.content.SharedPreferences
import com.abdullah.composeapp.data.network.RestApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }

    @Provides
    @Singleton
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Provides
    fun providesRetrofit(): RestApi {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestApi::class.java)
    }

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("FOOD_APP_SHARED_PREF", Context.MODE_PRIVATE)
    }

    @Provides
    fun providesGson(): Gson{
        return Gson()
    }


}