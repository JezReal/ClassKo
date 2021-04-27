package app.netlify.accessdeniedgc.classko.di

import android.content.Context
import androidx.room.Room
import app.netlify.accessdeniedgc.classko.database.ScheduleDao
import app.netlify.accessdeniedgc.classko.database.ScheduleDatabase
import app.netlify.accessdeniedgc.classko.network.ScheduleApi
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://evening-crag-20937.herokuapp.com/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideScheduleApi(): ScheduleApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ScheduleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideScheduleDatabase(@ApplicationContext context: Context): ScheduleDatabase {
        return Room.databaseBuilder(
            context,
            ScheduleDatabase::class.java,
            "schedule_database"
        ).build()
    }

    @Provides
    fun provideScheduleDao(database: ScheduleDatabase): ScheduleDao {
        return database.scheduleDao
    }

    @Provides
    fun provideScheduleRepository(api: ScheduleApi, dao: ScheduleDao) = ScheduleRepository(api, dao)

}