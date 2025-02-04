package com.tolstoy.zurichat.di

import com.tolstoy.zurichat.data.remoteSource.ApiServiceFactory
import com.tolstoy.zurichat.data.remoteSource.NewChannelApiService
import com.tolstoy.zurichat.data.remoteSource.UsersService
import com.tolstoy.zurichat.ui.newchannel.CreateChannelRemote
import com.tolstoy.zurichat.ui.newchannel.RetrofitChannelClient
import com.tolstoy.zurichat.ui.newchannel.remote.CreateChannelRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @get:Binds
    val CreateChannelRemoteImpl.createChannelRepositoryRemote: CreateChannelRemote

    companion object {
        @[Provides Singleton]
        fun provideApiService(): NewChannelApiService =
            ApiServiceFactory.createApiService()

        @ChannelRetrofitService
        @[Provides Singleton]
        fun provideRetrofitChannel(): UsersService =
            RetrofitChannelClient.getRetrofitService()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChannelRetrofitService