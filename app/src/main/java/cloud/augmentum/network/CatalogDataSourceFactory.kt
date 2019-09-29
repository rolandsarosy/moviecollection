package cloud.augmentum.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cloud.augmentum.data.domainobjects.MovieData
import io.reactivex.disposables.CompositeDisposable

class CatalogDataSourceFactory(private val endpoint: Endpoint, private val compositeDisposable: CompositeDisposable) :
    DataSource.Factory<Int, MovieData>() {

    val catalogDataSource = MutableLiveData<CatalogDataSource>()

    override fun create(): DataSource<Int, MovieData> {
        val dataSource = CatalogDataSource(endpoint, compositeDisposable)
        catalogDataSource.postValue(dataSource)
        return dataSource
    }
}