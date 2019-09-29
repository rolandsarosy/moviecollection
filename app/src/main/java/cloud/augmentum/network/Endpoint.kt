package cloud.augmentum.network

import cloud.augmentum.BuildConfig
import cloud.augmentum.data.apiobjects.ApiMovieDetailData
import cloud.augmentum.data.apiobjects.ApiMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int = 1, @Query("api_key") apiKey: String = BuildConfig.API_KEY): Single<ApiMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int = 1, @Query("api_key") apiKey: String = BuildConfig.API_KEY): Single<ApiMovieResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") movieId: Long, @Query("api_key") apiKey: String = BuildConfig.API_KEY): Single<ApiMovieDetailData>
}