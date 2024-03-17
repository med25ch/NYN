package com.example.nyn.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val USER_PREFERENCES = "user_preferences"

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}


class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
){

    companion object {
        private const val ALL_NOTES = "All notes"
    }

    //Read user preferences
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
                exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->

        val sortCategoryString = preferences[PreferencesKeys.SORT_CATEGORY_STRING] ?: ALL_NOTES
        UserPreferences(sortCategoryString)
    }

    // Write user preferences
    suspend fun updateCategorySort(sortCategoryString: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_CATEGORY_STRING] = sortCategoryString
        }
    }


}

private object PreferencesKeys {
    val SORT_CATEGORY_STRING = stringPreferencesKey("sort_category_string")
}

data class UserPreferences(var sortCategoryString: String = "All notes")
