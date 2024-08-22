package com.zhalz.voyageoflife.data.local.room.story

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhalz.voyageoflife.data.remote.response.StoryData

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<StoryData>)

    @Query("SELECT * FROM story_entity")
    fun getStories(): PagingSource<Int, StoryData>

    @Query("DELETE FROM story_entity")
    suspend fun deleteStories()

}