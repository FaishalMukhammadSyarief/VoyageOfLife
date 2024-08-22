package com.zhalz.voyageoflife.data.local.room.story

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_entity")
data class StoryEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val photoUrl: String? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val createdAt: String? = null,
)