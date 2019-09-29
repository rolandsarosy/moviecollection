package cloud.augmentum.data.domainobjects

import cloud.augmentum.database.entity.FavouriteMovieEntity

data class MovieDetailData(
    val id: Long = 0,
    val title: String = "",
    val tagline: String = "",
    val backdropPath: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val rating: String = "",
    val votes: String = ""
)

fun MovieDetailData.asFavouriteMovieEntity() = FavouriteMovieEntity(
    id,
    title,
    tagline,
    backdropPath,
    imageUrl,
    description,
    rating,
    votes
)

fun MovieDetailData.asMovieData() = MovieData(
    id,
    title,
    imageUrl,
    rating
)