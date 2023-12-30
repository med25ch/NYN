package com.example.nyn.di

import android.content.Context
import androidx.room.Room
import com.example.nyn.data.database.NotesDataBase
import com.example.nyn.data.models.category.NoteCategoryDAO
import com.example.nyn.data.models.note.NoteDAO
import com.example.nyn.data.repositories.OfflineCategoriesRepository
import com.example.nyn.data.repositories.OfflineNotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NotesDataBase {
        return Room.databaseBuilder(
            appContext,
            NotesDataBase::class.java,
            "notes_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideNoteDao(notesDataBase: NotesDataBase) : NoteDAO{
        return notesDataBase.noteDao()
    }

    @Provides
    @Singleton
    fun providesNotesRepository(noteDAO: NoteDAO): OfflineNotesRepository {
        return OfflineNotesRepository(noteDAO)
    }

    @Provides
    @Singleton
    fun provideNoteCategoryDao(notesDataBase: NotesDataBase) : NoteCategoryDAO{
        return notesDataBase.noteCategoryDAO()
    }
    @Provides
    @Singleton
    fun providesCategoriesRepository(noteCategoryDAO: NoteCategoryDAO): OfflineCategoriesRepository {
        return OfflineCategoriesRepository(noteCategoryDAO)
    }
}
