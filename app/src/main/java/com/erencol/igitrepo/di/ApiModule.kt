package com.erencol.igitrepo.di

import com.erencol.igitrepo.api.Api
import com.erencol.igitrepo.api.ApiRequest
import com.erencol.igitrepo.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    @Provides
    fun provideApiRequestApi(): Api {
       return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(Api::class.java)
    }
}