package cloud.augmentum.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cloud.augmentum.database.dao.FavouriteMovieDao
import cloud.augmentum.database.entity.FavouriteMovieEntity

@Database(entities = [FavouriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movieCollectionDatabase"
    }

    abstract fun favouriteMovieDao(): FavouriteMovieDao
}