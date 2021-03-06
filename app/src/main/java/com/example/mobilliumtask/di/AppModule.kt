package com.example.mobilliumtask.di

import com.example.mobilliumtask.network.ApiInterface
import com.example.mobilliumtask.repository.MainRepository
import com.example.mobilliumtask.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofitApi(): ApiInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun getRepo(apiInterface: ApiInterface): MainRepository = MainRepository(apiInterface)


}