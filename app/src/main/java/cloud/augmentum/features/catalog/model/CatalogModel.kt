package cloud.augmentum.features.catalog.model

import cloud.augmentum.common.SortingType
import cloud.augmentum.data.apiobjects.asMovieDataList
import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.database.entity.asMovieData
import cloud.augmentum.network.Endpoint
import cloud.augmentum.repository.FavouriteMoviesRepository
import cloud.augmentum.repository.SharedPreferenceRepository
import io.reactivex.Single

class CatalogModel(
    private val endpoint: Endpoint,
    private val preferences: SharedPreferenceRepository,
    private val repository: FavouriteMoviesRepository
) {

    fun getPopularMovieList() = endpoint.getPopularMovies().map { it.asMovieDataList() }

    fun getTopRatedMovieList() = endpoint.getTopRatedMovies().map { it.asMovieDataList() }

    fun getSortingType() = preferences.loadSortingType()

    fun saveSortingType(sortingType: SortingType) = preferences.saveSortingType(sortingType)

    fun getFavouriteMovies(): Single<MutableList<MovieData>> = repository.loadFavouriteMovies()
        .toObservable()
        .flatMapIterable { it }
        .map { it.asMovieData() }
        .toList()
}