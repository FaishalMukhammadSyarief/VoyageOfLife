package com.zhalz.voyageoflife.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.zhalz.voyageoflife.data.local.room.story.StoryEntity
import com.zhalz.voyageoflife.data.remote.response.StoryData

object DataMapper {

    fun List<StoryData>.toStoryEntity() = map {
        StoryEntity(it.id, it.name, it.photoUrl, it.description, it.lon,  it.lat, it.createdAt)
    }

    fun toStoryData(story: PagingData<StoryEntity>) = story.map {
        StoryData(it.id, it.name, it.photoUrl, it.description, it.lon, it.lat, it.createdAt)
    }

}