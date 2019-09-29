package cloud.augmentum.features.favouritemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cloud.augmentum.R
import cloud.augmentum.databinding.FragmentFavouriteMoviesBinding
import cloud.augmentum.features.favouritemovies.viewmodel.FavouriteMoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteMoviesFragment : Fragment() {

    private val viewModel: FavouriteMoviesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFavouriteMoviesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
        return binding.root
    }
}