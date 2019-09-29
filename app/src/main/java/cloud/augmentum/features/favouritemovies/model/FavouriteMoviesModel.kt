package cloud.augmentum.features.favouritemovies.model

import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.database.entity.asMovieData
import cloud.augmentum.repository.FavouriteMoviesRepository
import io.reactivex.Single

class FavouriteMoviesModel(private val repository: FavouriteMoviesRepository) {

    fun getFavouriteMovies(): Single<MutableList<MovieData>> = repository.loadFavouriteMovies()
        .toObservable()
        .flatMapIterable { it }
        .map { it.asMovieData() }
        .toList()
}