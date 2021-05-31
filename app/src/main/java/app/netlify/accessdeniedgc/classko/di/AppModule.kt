package app.netlify.accessdeniedgc.classko.di

import android.content.Context
import androidx.room.Room
import app.netlify.accessdeniedgc.classko.database.ScheduleDao
import app.netlify.accessdeniedgc.classko.database.ScheduleDatabase
import app.netlify.accessdeniedgc.classko.datastore.ClassKoDataStore
import app.netlify.accessdeniedgc.classko.network.ClassKoApi
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val CLASSKO_API_BASE_URL = "https://classko-backend.herokuapp.com/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideClassKoApi(): ClassKoApi {
        return Retrofit.Builder()
            .baseUrl(CLASSKO_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ClassKoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideScheduleDatabase(@ApplicationContext context: Context): ScheduleDatabase {
        return Room.databaseBuilder(
            context,
            ScheduleDatabase::class.java,
            "schedule_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): ClassKoDataStore {
        return ClassKoDataStore(context)
    }

    @Provides
    fun provideScheduleDao(database: ScheduleDatabase): ScheduleDao {
        return database.scheduleDao
    }

    @Provides
    fun provideScheduleRepository(api: ClassKoApi, dao: ScheduleDao) = ScheduleRepository(api, dao)

}