package cloud.augmentum.features.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cloud.augmentum.R
import cloud.augmentum.common.withArgs
import cloud.augmentum.databinding.FragmentMovieDetailBinding
import cloud.augmentum.features.moviedetail.viewmodel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModel()

    companion object {
        @JvmStatic
        fun newInstance(id: Long) = MovieDetailFragment().withArgs {
            putLong(MOVIE_ID, id)
        }

        const val MOVIE_ID = "movieId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val movieId = bundle.getLong(MOVIE_ID)
            viewModel.checkIfMovieIsFavourite(movieId)
            viewModel.getMovieDetails(movieId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMovieDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
        return binding.root
    }
}