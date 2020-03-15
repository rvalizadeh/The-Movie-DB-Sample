package com.themoviedb.model

import Dates
import com.google.gson.annotations.SerializedName


data class NowPlayingResponse (

    @SerializedName("results") val results : MutableList<Results>,
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("dates") val dates : Dates,
    @SerializedName("total_pages") val total_pages : Int

)