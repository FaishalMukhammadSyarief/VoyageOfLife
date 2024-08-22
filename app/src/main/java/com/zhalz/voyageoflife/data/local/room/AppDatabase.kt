package com.zhalz.voyageoflife.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhalz.voyageoflife.data.local.room.keys.RemoteKeys
import com.zhalz.voyageoflife.data.local.room.keys.RemoteKeysDao
import com.zhalz.voyageoflife.data.local.room.story.StoryDao
import com.zhalz.voyageoflife.data.local.room.story.StoryEntity

@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, context.packageName)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            return instance
        }
    }

}