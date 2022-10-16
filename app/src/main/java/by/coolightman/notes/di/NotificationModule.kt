package by.coolightman.notes.di

import android.app.AlarmManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext appContext: Context): AlarmManager {
        return appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}