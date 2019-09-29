package cloud.augmentum.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import cloud.augmentum.data.domainobjects.MovieData
import cloud.augmentum.data.domainobjects.MovieDetailData

@Entity
data class FavouriteMovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val tagline: String,
    val backdropPath: String,
    val imageUrl: String,
    val description: String,
    val rating: String,
    val votes: String
)

fun FavouriteMovieEntity.asMovieDetailData() = MovieDetailData(
    id,
    title,
    tagline,
    backdropPath,
    imageUrl,
    description,
    rating,
    votes
)

fun FavouriteMovieEntity.asMovieData() = MovieData(
    id,
    title,
    imageUrl,
    rating
)