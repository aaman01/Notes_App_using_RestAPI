package com.example.notesapprestapi.di

import com.example.notesapprestapi.api.AuthInterceptor
import com.example.notesapprestapi.api.Notesapi
import com.example.notesapprestapi.api.UserAPI
import com.example.notesapprestapi.utils.Const.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {


    //return retrofit builder
    @Singleton
    @Provides
    fun providesRetrofitBuilder():Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit.Builder):UserAPI{
        return  retrofit.build().create(UserAPI::class.java)

    }
    //okk http client
   @Singleton
   @Provides
    fun providesokhttpclient(authInterceptor: AuthInterceptor):OkHttpClient{
     return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesNotesAPI(retrofit: Retrofit.Builder,okHttpClient: OkHttpClient):Notesapi {
        return  retrofit
            .client(okHttpClient)
            .build().create(Notesapi::class.java)

    }

}