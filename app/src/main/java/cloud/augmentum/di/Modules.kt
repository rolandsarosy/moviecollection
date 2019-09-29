package cloud.augmentum.di

import androidx.room.Room
import cloud.augmentum.BuildConfig
import cloud.augmentum.database.AppDatabase
import cloud.augmentum.features.catalog.model.CatalogModel
import cloud.augmentum.features.catalog.viewmodel.CatalogViewModel
import cloud.augmentum.features.favouritemovies.model.FavouriteMoviesModel
import cloud.augmentum.features.favouritemovies.viewmodel.FavouriteMoviesViewModel
import cloud.augmentum.features.moviedetail.model.MovieDetailModel
import cloud.augmentum.features.moviedetail.viewmodel.MovieDetailViewModel
import cloud.augmentum.network.AuthenticationInterceptor
import cloud.augmentum.network.CatalogDataSource
import cloud.augmentum.network.CatalogDataSourceFactory
import cloud.augmentum.network.Endpoint
import cloud.augmentum.repository.FavouriteMoviesRepository
import cloud.augmentum.repository.SharedPreferenceRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val OKHTTP_INTERCEPTOR = "OkhttpInterceptor"
private const val TIME_OUT_IN_SECONDS: Long = 30
private const val RETROFIT_CLIENT = "RetrofitClient"

val applicationModule = module {
    single { SharedPreferenceRepository(androidContext()) }
}

val viewModelModules = module {
    viewModel { CatalogViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { FavouriteMoviesViewModel(get()) }
}

val modelModules = module {
    factory { CatalogModel(get(), get(), get()) }
    factory { MovieDetailModel(get(), get()) }
    factory { FavouriteMoviesModel(get()) }
}

val networkModule = module {
    single { GsonBuilder().serializeNulls().create() }
    single(named(OKHTTP_INTERCEPTOR)) { createOkhttpClient().build() }
    single(named(RETROFIT_CLIENT)) { createRetrofitClient(get(), get(named(OKHTTP_INTERCEPTOR))) }
    single { createEndpoint(get(named(RETROFIT_CLIENT))) }
    single { CompositeDisposable() }
    single { CatalogDataSource(get(), get()) }
    single { CatalogDataSourceFactory(get(), get()) }
}

val databaseModule = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME).build() }
    single { createFavouriteMovieDao(get()) }
    single { FavouriteMoviesRepository(get()) }
}

private fun createOkhttpClient(): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.addInterceptor(httpLoggingInterceptor)
    return builder
}

private fun createRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun createEndpoint(retrofit: Retrofit): Endpoint {
    return retrofit.create(Endpoint::class.java)
}

private fun createFavouriteMovieDao(database: AppDatabase) = database.favouriteMovieDao()