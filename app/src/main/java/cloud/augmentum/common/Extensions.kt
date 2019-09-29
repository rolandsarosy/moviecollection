package cloud.augmentum.common

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun Context.convertDpToPixel(dipValue: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)

fun waitAndRun(delay: Long, doOnComplete: () -> Unit) {
    Completable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).doOnComplete {
        doOnComplete()
    }.subscribe()
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

fun <T> MutableLiveData<T>.safeValue(default: T): T = value ?: default