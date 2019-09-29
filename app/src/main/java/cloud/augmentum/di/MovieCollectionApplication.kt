package cloud.augmentum.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MovieCollectionApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MovieCollectionApplication)
            androidFileProperties()
            modules(
                listOf(
                    applicationModule,
                    modelModules,
                    viewModelModules,
                    networkModule,
                    databaseModule
                )
            )
        }
        Timber.plant(Timber.DebugTree())
    }
}