package cloud.augmentum.repository

import android.content.Context
import androidx.core.content.edit
import cloud.augmentum.common.SortingType
import cloud.augmentum.common.getSortingTypeById

class SharedPreferenceRepository(private val context: Context) {

    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREF_NAME = "MovieCollectionUserPreferences"
        private const val PREF_SORTING_TYPE = "SortingType"
    }

    private fun getSharedPreference() = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

    fun saveSortingType(sortingType: SortingType) {
        val preference = getSharedPreference()
        preference.edit {
            putInt(PREF_SORTING_TYPE, sortingType.id)
        }
    }

    fun loadSortingType(): SortingType {
        val preference = getSharedPreference()
        val sortingTypeId = preference.getInt(PREF_SORTING_TYPE, SortingType.MOST_POPULAR.id)
        return getSortingTypeById(sortingTypeId)
    }
}