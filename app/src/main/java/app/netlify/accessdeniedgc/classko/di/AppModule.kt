package app.netlify.accessdeniedgc.classko.di

import app.netlify.accessdeniedgc.classko.network.GoogleCalendarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://www.googleapis.com/calendar/v3/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGoogleCalendarApi(): GoogleCalendarApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GoogleCalendarApi::class.java)
    }
}