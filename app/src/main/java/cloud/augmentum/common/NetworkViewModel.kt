package cloud.augmentum.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cloud.augmentum.common.events.ErrorEvent
import cloud.augmentum.common.events.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class NetworkViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val errorEvent = MutableLiveData<Event<ErrorEvent>>()
    val navigationEvent = MutableLiveData<Event<Int>>()

    fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}