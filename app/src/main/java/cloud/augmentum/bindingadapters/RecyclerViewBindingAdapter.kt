package cloud.augmentum.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cloud.augmentum.common.recyclerview.AdapterType
import cloud.augmentum.common.recyclerview.ListItemViewModel
import cloud.augmentum.common.recyclerview.RecyclerViewAdapter

object RecyclerViewBindingAdapter {

    @BindingAdapter("source", "adapterType", "numberOfColumns", requireAll = false)
    @JvmStatic
    fun setupRecyclerView(
        recyclerView: RecyclerView,
        source: List<ListItemViewModel>,
        adapterType: AdapterType,
        numberOfColumns: Int
    ) {
        recyclerView.layoutManager = when (adapterType) {
            AdapterType.LINEAR_LAYOUT_MANAGER -> LinearLayoutManager(recyclerView.context)
            AdapterType.GRID_LAYOUT_MANAGER -> GridLayoutManager(recyclerView.context, numberOfColumns)
            AdapterType.STAGGERED_LAYOUT_MANAGER -> StaggeredGridLayoutManager(
                numberOfColumns, StaggeredGridLayoutManager.VERTICAL
            )
        }
        val adapter = RecyclerViewAdapter()
        adapter.items = source.toMutableList()
        recyclerView.adapter = adapter
    }
}