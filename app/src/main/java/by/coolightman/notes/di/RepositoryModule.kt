package by.coolightman.notes.di

import by.coolightman.notes.data.repository.NoteRepositoryImpl
import by.coolightman.notes.data.repository.NotificationRepositoryImpl
import by.coolightman.notes.data.repository.PreferencesRepositoryImpl
import by.coolightman.notes.data.repository.TaskRepositoryImpl
import by.coolightman.notes.domain.repository.NoteRepository
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.PreferencesRepository
import by.coolightman.notes.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    fun provideTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    fun providePreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    @Singleton
    fun provideNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository
}