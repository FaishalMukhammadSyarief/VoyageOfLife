package com.zhalz.voyageoflife.utils

import com.zhalz.voyageoflife.data.remote.response.StoryData

object DataDummy {

    fun generateDummyStory(): List<StoryData> {
        return (0..100).map {
            StoryData(
                "photo $it",
                "createdAt $it",
                "name $it",
                "description $it",
                null,
                it.toString(),
                null,
            )
        }
    }

}