package com.example.pawsome.dependencyInjection

import android.content.Context
import com.example.pawsome.R
import com.example.pawsome.api.ApiConstants
import com.example.pawsome.api.BackEndApi
import com.example.pawsome.api.EKYCApi
import com.example.pawsome.api.MyInterceptor
import com.example.pawsome.data.repository.AuthRepo
import com.example.pawsome.data.repository.AuthRepoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepo {
        return AuthRepoImpl(firebaseAuth)
    }

    @Provides
    fun provideOfflinePluginFactory(@ApplicationContext context: Context) =
        StreamOfflinePluginFactory(
            appContext = context
        )

    @Provides
    fun statePluginFactory(@ApplicationContext context: Context) =
        StreamStatePluginFactory(
            config = StatePluginConfig(),
            appContext = context
        )

    @Provides
    @Singleton
    fun provideChatClient(@ApplicationContext context: Context, offlinePluginFactory: StreamOfflinePluginFactory, statePluginFactory: StreamStatePluginFactory) =
        ChatClient.Builder(context.getString(R.string.api_key), context)
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .logLevel(ChatLogLevel.ALL)
            .build()

    @Provides
    @Singleton
    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(MyInterceptor())
        }.build()
    }

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): BackEndApi {
        return builder
            .build()
            .create(BackEndApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    // For eKYC
    @Provides
    @Singleton
    fun provideEKYCApi(client: OkHttpClient): EKYCApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.EKYC_DOMAIN)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EKYCApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideEKYCRetrofit(client: OkHttpClient): Retrofit.Builder {
//        return Retrofit.Builder()
//            .baseUrl(ApiConstants.EKYC_DOMAIN)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//    }
}