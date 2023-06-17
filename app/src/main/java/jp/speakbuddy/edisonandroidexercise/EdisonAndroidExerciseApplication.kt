package jp.speakbuddy.edisonandroidexercise

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.data.FactRepository
import jp.speakbuddy.edisonandroidexercise.data.FactRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@HiltAndroidApp
class EdisonAndroidExerciseApplication : Application()

@Module
@InstallIn(SingletonComponent::class)
abstract class MyModule {
    @Binds
    @Singleton
    abstract fun bindFactRepositoryImpl(
        myRepositoryImpl: FactRepositoryImpl
    ): FactRepository
//
//    @Binds
//    @Singleton
//    abstract fun bindFactsApi(
//        myFactsApiImpl: FactsApiImpl
//    ): FactsApi
}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher