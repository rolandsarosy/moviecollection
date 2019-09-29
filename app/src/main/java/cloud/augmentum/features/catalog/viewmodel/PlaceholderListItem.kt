package cloud.augmentum.features.catalog.viewmodel

import cloud.augmentum.R
import cloud.augmentum.common.recyclerview.ListItemViewModel

class PlaceholderListItem : ListItemViewModel() {
    override fun getViewType(): Int {
        return R.layout.list_item_placeholder
    }

    override fun areItemsTheSame(newItem: ListItemViewModel): Boolean {
        return false
    }

    override fun areContentsTheSame(newItem: ListItemViewModel): Boolean {
        return false
    }
}