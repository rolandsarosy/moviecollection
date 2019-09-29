package cloud.augmentum.features.catalog

import android.view.View

interface MovieCardClickCallback {
    fun onMovieCardClicked(id: Long, view: View)
}