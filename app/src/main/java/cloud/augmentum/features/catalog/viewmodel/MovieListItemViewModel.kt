package cloud.augmentum.features.catalog.viewmodel

import android.view.View
import cloud.augmentum.R
import cloud.augmentum.common.recyclerview.ListItemViewModel
import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.features.catalog.MovieCardClickCallback

class MovieListItemViewModel(val data: MovieData, private val callback: MovieCardClickCallback) : ListItemViewModel() {

    var movieName: String = data.name
    var imageUrl: String = data.imageUrl
    var rating: String = data.rating

    override fun getViewType(): Int {
        return R.layout.list_item_movie
    }

    override fun areItemsTheSame(newItem: ListItemViewModel): Boolean {
        return isSame(newItem)
    }

    override fun areContentsTheSame(newItem: ListItemViewModel): Boolean {
        return isSame(newItem)
    }

    fun onMovieClicked(view: View) {
        callback.onMovieCardClicked(data.id, view)
    }

    private fun isSame(newItem: ListItemViewModel): Boolean {
        return if (newItem is MovieListItemViewModel) {
            newItem.data.id == data.id
        } else {
            false
        }
    }
}