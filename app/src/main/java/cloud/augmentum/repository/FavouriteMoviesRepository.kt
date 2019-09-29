package cloud.augmentum.repository

import cloud.augmentum.database.dao.FavouriteMovieDao
import cloud.augmentum.database.entity.FavouriteMovieEntity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavouriteMoviesRepository(private val dao: FavouriteMovieDao) {

    fun saveFavouriteMovie(entity: FavouriteMovieEntity) {
        Timber.d("New movie marked as favourite. ID: ${entity.id}")
        Completable.fromAction {
            dao.insert(entity)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteFavouriteMovieById(id: Long) {
        Completable.fromAction {
            dao.deleteById(id)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun loadFavouriteMovies() = dao.loadAllFavouriteMovies()

    fun isMovieFavourite(id: Long) = dao.isMovieFavourite(id)
}