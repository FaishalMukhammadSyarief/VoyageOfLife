package com.zhalz.voyageoflife

import com.zhalz.voyageoflife.data.remote.response.StoryData

object DataDummy {
    fun generateDummyStoryData(): List<StoryData> {
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