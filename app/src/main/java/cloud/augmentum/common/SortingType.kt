package cloud.augmentum.common

enum class SortingType(val id: Int) {
    MOST_POPULAR(0), TOP_RATED(1), FAVOURITES(2)
}

fun getSortingTypeById(id: Int) = when (id) {
    0 -> SortingType.MOST_POPULAR
    1 -> SortingType.TOP_RATED
    2 -> SortingType.FAVOURITES
    else -> throw IllegalArgumentException("Application tried to access non-existent SortingType.")
}