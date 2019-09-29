package cloud.augmentum.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImageWithGlide(imageView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(imageView.context)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("src")
    fun loadImage(imageView: ImageView, resId: Int) {
        imageView.setImageResource(resId)
    }
}