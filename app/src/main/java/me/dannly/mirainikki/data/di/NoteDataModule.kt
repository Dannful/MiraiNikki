package me.dannly.mirainikki.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.dannly.mirainikki.data.NoteDatabase
import me.dannly.mirainikki.data.repository.DispatcherProviderImpl
import me.dannly.mirainikki.data.repository.NoteRepositoryImpl
import me.dannly.mirainikki.domain.repository.DispatcherProvider
import me.dannly.mirainikki.domain.repository.NoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteDataModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "Notes"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository = NoteRepositoryImpl(
        noteDatabase.dao
    )

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}