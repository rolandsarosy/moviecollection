package cloud.augmentum.features.moviedetail.model

import cloud.augmentum.data.apiobjects.asMovieDetailData
import cloud.augmentum.data.domainobjects.MovieDetailData
import cloud.augmentum.data.domainobjects.asFavouriteMovieEntity
import cloud.augmentum.network.Endpoint
import cloud.augmentum.repository.FavouriteMoviesRepository

// TODO: Improve RX Chaining. If record is favourited, then it exists already. Use caching in these cases.
class MovieDetailModel(private val endpoint: Endpoint, private val repository: FavouriteMoviesRepository) {

    fun getMovieDetails(id: Long) = endpoint.getMovieDetails(id).map { it.asMovieDetailData() }

    fun isMovieFavourite(id: Long) = repository.isMovieFavourite(id)

    fun markMovieAsFavourite(data: MovieDetailData) = repository.saveFavouriteMovie(data.asFavouriteMovieEntity())

    fun deleteMovieFromFavourites(id: Long) = repository.deleteFavouriteMovieById(id)
}