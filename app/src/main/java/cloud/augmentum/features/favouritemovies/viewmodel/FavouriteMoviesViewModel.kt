package cloud.augmentum.features.favouritemovies.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.findNavController
import cloud.augmentum.R
import cloud.augmentum.common.NetworkViewModel
import cloud.augmentum.common.default
import cloud.augmentum.common.events.ErrorEvent
import cloud.augmentum.common.events.Event
import cloud.augmentum.common.recyclerview.ListItemViewModel
import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.features.catalog.MovieCardClickCallback
import cloud.augmentum.features.catalog.viewmodel.MovieListItemViewModel
import cloud.augmentum.features.catalog.viewmodel.PlaceholderListItem
import cloud.augmentum.features.favouritemovies.model.FavouriteMoviesModel
import cloud.augmentum.features.moviedetail.MovieDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavouriteMoviesViewModel(private val model: FavouriteMoviesModel) : NetworkViewModel(), LifecycleObserver,
    MovieCardClickCallback {

    val listItemViewModels = MutableLiveData<List<ListItemViewModel>>().default(emptyList())
    val isListEmpty = MutableLiveData<Boolean>().default(true)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getFavouriteMovies() {
        val disposable = model.getFavouriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> handleQueryResultSuccess(result) }, { error -> handleQueryResultError(error) })
        add(disposable)
    }

    private fun handleQueryResultSuccess(result: List<MovieData>) {
        Timber.d("Received favourite movie data from the database")
        isListEmpty.value = result.isEmpty()
        val newListItemViewModels = result.map { movieData ->
            MovieListItemViewModel(movieData, this)
        }
        val catalogItems = mutableListOf<ListItemViewModel>(PlaceholderListItem())
        catalogItems.addAll(newListItemViewModels)
        listItemViewModels.postValue(catalogItems)
    }

    private fun handleQueryResultError(error: Throwable?) {
        Timber.e(error, "There was an error while requesting information from the database")
        errorEvent.postValue(Event(ErrorEvent.SHOW_ERROR))
    }

    override fun onMovieCardClicked(id: Long, view: View) {
        val bundle = Bundle().apply {
            putLong(MovieDetailFragment.MOVIE_ID, id)
        }
        view.findNavController().navigate(R.id.action_favouriteMoviesFragment_to_movieDetailFragment, bundle)
    }

    fun onHomeClicked(view: View) {
        view.findNavController().popBackStack()
    }
}