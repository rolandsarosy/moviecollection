package cloud.augmentum.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cloud.augmentum.database.entity.FavouriteMovieEntity
import io.reactivex.Single

@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM favouritemovieentity")
    fun loadAllFavouriteMovies(): Single<List<FavouriteMovieEntity>>

    @Query("SELECT COUNT(*) from favouritemovieentity WHERE id = :id")
    fun isMovieFavourite(id: Long): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteMovieEntity)

    @Query("DELETE FROM favouritemovieentity WHERE id = :id")
    fun deleteById(id: Long): Int
}