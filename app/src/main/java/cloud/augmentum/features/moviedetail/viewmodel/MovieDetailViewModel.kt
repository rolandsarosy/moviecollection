package cloud.augmentum.features.moviedetail.viewmodel

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import cloud.augmentum.common.NetworkViewModel
import cloud.augmentum.common.default
import cloud.augmentum.common.events.ErrorEvent
import cloud.augmentum.common.events.Event
import cloud.augmentum.common.safeValue
import cloud.augmentum.data.domainobjects.MovieDetailData
import cloud.augmentum.features.moviedetail.model.MovieDetailModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MovieDetailViewModel(private val model: MovieDetailModel) : NetworkViewModel(), LifecycleObserver {

    val data = MutableLiveData<MovieDetailData>().default(MovieDetailData())
    val isFavourite = MutableLiveData<Boolean>().default(false)

    fun onFavouriteClicked(view: View) {
        if (isFavourite.value == false) {
            model.markMovieAsFavourite(data.safeValue(MovieDetailData()))
            isFavourite.postValue(true)
        } else {
            model.deleteMovieFromFavourites(data.safeValue(MovieDetailData()).id)
            isFavourite.postValue(false)
        }
    }

    fun getMovieDetails(id: Long) {
        val disposable = model.getMovieDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> handleMovieDetailSuccess(result) }, { error -> handleMovieDetailError(error) })
        add(disposable)
    }

    fun checkIfMovieIsFavourite(id: Long) {
        val disposable = model.isMovieFavourite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { queryResult -> handleQuerySuccess(queryResult) },
                { error -> handleQueryError(error) })
        add(disposable)
    }

    private fun handleMovieDetailSuccess(result: MovieDetailData) {
        Timber.d("Successfully fetched movie detail information from network.")
        data.postValue(result)
    }

    private fun handleQuerySuccess(queryResult: Int?) {
        Timber.d("Query result value is: $queryResult")
        isFavourite.value = queryResult == 1
    }

    private fun handleMovieDetailError(error: Throwable) {
        Timber.e(error, "There was an error while fetching movie detail information from the network.")
        errorEvent.postValue(Event(ErrorEvent.SHOW_ERROR))
    }

    private fun handleQueryError(error: Throwable) {
        Timber.e(error, "There was an error while requesting database information.")
    }

    fun onBackClicked(view: View) {
        view.findNavController().popBackStack()
    }
}