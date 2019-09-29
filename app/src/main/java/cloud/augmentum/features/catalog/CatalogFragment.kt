package cloud.augmentum.features.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cloud.augmentum.R
import cloud.augmentum.common.SortingType
import cloud.augmentum.common.convertDpToPixel
import cloud.augmentum.databinding.FragmentCatalogBinding
import cloud.augmentum.features.catalog.viewmodel.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : Fragment() {

    private val viewModel: CatalogViewModel by viewModel()

    private var sortingDialog: SortingDialog? = null

    private val onPopularSortingSelected = View.OnClickListener {
        viewModel.changeSorting(SortingType.MOST_POPULAR)
        hideSortingDialog()
    }

    private val onTopSortingSelected = View.OnClickListener {
        viewModel.changeSorting(SortingType.TOP_RATED)
        hideSortingDialog()
    }

    private val onFavouritesSortingSelected = View.OnClickListener {
        viewModel.changeSorting(SortingType.FAVOURITES)
        hideSortingDialog()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CatalogFragment()

        private const val DEFAULT_ELEVATION = 10f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCatalogBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_catalog, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorEvent.observe(this, Observer { event ->
            // TODO: Create error dialog and show it here!
        })

        viewModel.dialogEvent.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { showSortingDialog() }
        })

        catalog_movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val firstItemVisibleIndex = intArrayOf(0, 0)
                layoutManager.findFirstCompletelyVisibleItemPositions(firstItemVisibleIndex)
                if (firstItemVisibleIndex.contains(0)) {
                    catalog_appbar.elevation = 0f
                } else {
                    catalog_appbar.elevation = recyclerView.context.convertDpToPixel(DEFAULT_ELEVATION)
                }
            }
        })
    }

    private fun showSortingDialog() {
        sortingDialog = childFragmentManager.findFragmentByTag(SortingDialog.SORTING_DIALOG) as? SortingDialog
        if (sortingDialog == null) {
            sortingDialog = SortingDialog(onTopSortingSelected, onPopularSortingSelected, onFavouritesSortingSelected)
        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        sortingDialog?.show(fragmentTransaction, SortingDialog.SORTING_DIALOG)
    }

    private fun hideSortingDialog() {
        sortingDialog?.dismiss()
    }
}