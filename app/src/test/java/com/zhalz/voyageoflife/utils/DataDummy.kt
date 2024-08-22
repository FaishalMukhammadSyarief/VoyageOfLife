package com.zhalz.voyageoflife.utils

import com.zhalz.voyageoflife.data.remote.response.StoryData

object DataDummy {

    fun generateDummyStory(): List<StoryData> {
        return (0..100).map {
            StoryData(
                it.toString(),
                "name $it",
                "description $it",
                "photo $it",
                null,
                null,
                "createdAt $it",
            )
        }
    }

}