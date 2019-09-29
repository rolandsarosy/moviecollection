package cloud.augmentum.features.catalog.viewmodel

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.findNavController
import cloud.augmentum.R
import cloud.augmentum.common.NetworkViewModel
import cloud.augmentum.common.SortingType
import cloud.augmentum.common.default
import cloud.augmentum.common.events.DialogEvent
import cloud.augmentum.common.events.ErrorEvent
import cloud.augmentum.common.events.Event
import cloud.augmentum.common.recyclerview.ListItemViewModel
import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.features.catalog.MovieCardClickCallback
import cloud.augmentum.features.catalog.model.CatalogModel
import cloud.augmentum.features.moviedetail.MovieDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CatalogViewModel(private val model: CatalogModel) : NetworkViewModel(), LifecycleObserver,
    MovieCardClickCallback {

    val listItemViewModels = MutableLiveData<List<ListItemViewModel>>().default(emptyList())
    val dialogEvent = MutableLiveData<Event<DialogEvent>>()
    val isListEmpty = MutableLiveData<Boolean>().default(true)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun getMovieList() {
        when (model.getSortingType()) {
            SortingType.MOST_POPULAR -> getPopularMoviesList()
            SortingType.TOP_RATED -> getTopMoviesList()
            SortingType.FAVOURITES -> getFavouriteMoviesList()
        }
    }

    private fun getPopularMoviesList() {
        Timber.d("Loading most popular movie list")
        val disposable = model.getPopularMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> handleMovieListSuccess(result) }, { error -> handleMovieListError(error) })
        add(disposable)
    }

    private fun getTopMoviesList() {
        Timber.d("Loading top rated movie list")
        val disposable = model.getTopRatedMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> handleMovieListSuccess(result) }, { error -> handleMovieListError(error) })
        add(disposable)
    }

    private fun getFavouriteMoviesList() {
        Timber.d("Loading favourite movies")
        val disposable = model.getFavouriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> handleMovieListSuccess(result) }, { error -> handleMovieListError(error) })
        add(disposable)
    }

    private fun handleMovieListSuccess(result: List<MovieData>) {
        Timber.d("Successfully fetched the movie list.")
        isListEmpty.value = result.isEmpty()
        val newListItemViewModels = result.map { movieData ->
            MovieListItemViewModel(movieData, this)
        }
        val catalogItems = mutableListOf<ListItemViewModel>(PlaceholderListItem(), PlaceholderListItem())
        catalogItems.addAll(newListItemViewModels)
        listItemViewModels.postValue(catalogItems)
    }

    private fun handleMovieListError(error: Throwable) {
        Timber.e(error, "There was an error while fetching the movie list.")
        errorEvent.postValue(Event(ErrorEvent.SHOW_ERROR))
    }

    override fun onMovieCardClicked(id: Long, view: View) {
        val bundle = Bundle().apply {
            putLong(MovieDetailFragment.MOVIE_ID, id)
        }
        view.findNavController().navigate(R.id.action_catalogFragment_to_movieDetailFragment, bundle)
    }

    fun onSortClicked(view: View) {
        dialogEvent.value = Event(DialogEvent.SHOW_DIALOG)
    }

    fun onFavouritesClicked(view: View) {
        view.findNavController().navigate(R.id.action_catalogFragment_to_favouriteMoviesFragment)
    }

    // TODO: Change reloading method when implementing pagination.
    fun changeSorting(sortingType: SortingType) {
        model.saveSortingType(sortingType)
        getMovieList()
    }
}
