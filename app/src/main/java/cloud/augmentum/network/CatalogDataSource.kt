package cloud.augmentum.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import cloud.augmentum.data.apiobjects.asMovieDataList
import cloud.augmentum.data.domainobjects.MovieData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CatalogDataSource(private val endpoint: Endpoint, private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Int, MovieData>() {
    val state = MutableLiveData<NetworkState>()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieData>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(endpoint.getPopularMovies(1)
            .map { it.asMovieDataList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                updateState(NetworkState.DONE)
                callback.onResult(response, null, 2)
            }, {
                updateState(NetworkState.ERROR)
                setRetry(Action { loadInitial(params, callback) })
            })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(endpoint.getPopularMovies(1)
            .map { it.asMovieDataList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    updateState(NetworkState.DONE)
                    callback.onResult(response, params.key + 1)
                }, {
                    updateState(NetworkState.ERROR)
                    setRetry(Action { loadAfter(params, callback) })
                }
            )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {
        // Not implemented
    }

    private fun updateState(state: NetworkState) {
        this.state.postValue(state)
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}