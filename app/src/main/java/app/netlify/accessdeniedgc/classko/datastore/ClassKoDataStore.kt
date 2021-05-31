package app.netlify.accessdeniedgc.classko.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClassKoDataStore(val context: Context) {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "classko-datastore")

    companion object {
        val USER_JWT_TOKEN = stringPreferencesKey("user_jwt_token")
    }

    suspend fun storeToken(token: String) {
        context.dataStore.edit {
            it[USER_JWT_TOKEN] = token
        }
    }

    val tokenFlow: Flow<String> = context.dataStore.data.map {
        it[USER_JWT_TOKEN] ?: ""
    }
}