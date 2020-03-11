package com.themoviedb.models

import Dates
import Results
import com.google.gson.annotations.SerializedName


data class NowPlayingResponseModel (

    @SerializedName("results") val results : List<Results>,
    @SerializedName("page") val page : Int,
    @SerializedName("total_results") val total_results : Int,
    @SerializedName("dates") val dates : Dates,
    @SerializedName("total_pages") val total_pages : Int

)