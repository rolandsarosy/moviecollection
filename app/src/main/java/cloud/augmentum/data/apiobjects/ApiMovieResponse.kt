package cloud.augmentum.data.apiobjects

import cloud.augmentum.data.domainobjects.MovieData

data class ApiMovieResponse(
    val page: Int,
    val results: List<ApiMovieData>,
    val total_results: Int,
    val total_pages: Int
)

private const val IMAGE_URL_SEGMENT = "https://image.tmdb.org/t/p/w500/"

fun ApiMovieResponse.asMovieDataList() = results.map { movieData ->
    MovieData(
        id = movieData.id,
        name = movieData.title,
        imageUrl = "$IMAGE_URL_SEGMENT${movieData.poster_path}",
        rating = movieData.vote_average.toString()
    )
}