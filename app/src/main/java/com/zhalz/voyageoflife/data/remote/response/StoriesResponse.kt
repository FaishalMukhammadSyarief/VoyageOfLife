package com.zhalz.voyageoflife.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<StoryData?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null

)
