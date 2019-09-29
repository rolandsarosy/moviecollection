package cloud.augmentum.data.apiobjects

import cloud.augmentum.data.domainobjects.MovieDetailData
import java.util.*

data class ApiMovieDetailData(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: ApiCollection?,
    val budget: Long,
    val genres: List<ApiGenre>,
    val homePage: String,
    val id: Long,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ApiProductionCompany>,
    val production_countries: List<ApiProductionCountry>,
    val release_date: Date,
    val revenue: Long,
    val runtime: Int,
    val spoken_languages: List<ApiSpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Long
)

private const val IMAGE_URL_SEGMENT = "https://image.tmdb.org/t/p/w500/"

fun ApiMovieDetailData.asMovieDetailData() = MovieDetailData(
    id = id,
    title = title,
    tagline = tagline ?: "",
    backdropPath = "$IMAGE_URL_SEGMENT${backdrop_path}",
    imageUrl = "$IMAGE_URL_SEGMENT${poster_path}",
    description = overview ?: "",
    rating = vote_average.toString(),
    votes = vote_count.toString()
)