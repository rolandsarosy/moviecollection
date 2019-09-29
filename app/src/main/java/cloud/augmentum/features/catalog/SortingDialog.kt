package cloud.augmentum.features.catalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cloud.augmentum.R
import kotlinx.android.synthetic.main.dialog_sort.*

class SortingDialog(
    private val topClickedListener: View.OnClickListener,
    private val popularClickedListener: View.OnClickListener,
    private val favouritesClickedListener: View.OnClickListener
) :
    DialogFragment() {

    companion object {
        const val SORTING_DIALOG = "SortingDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sorting_popular.setOnClickListener(popularClickedListener)
        sorting_top.setOnClickListener(topClickedListener)
        sorting_favourites.setOnClickListener(favouritesClickedListener)
    }
}