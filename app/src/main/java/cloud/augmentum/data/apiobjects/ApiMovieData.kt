package cloud.augmentum.data.apiobjects

data class ApiMovieData(
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Float,
    val vote_count: Long,
    val video: Boolean,
    val vote_average: Double
)