package com.agungfir.jetpackcinema.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("poster_path")
    var poster_path: String? = null,

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null,

    @SerializedName("vote_average")
    var vote_average: Float? = null,

    @SerializedName("first_air_date")
    var first_air_date: String? = null

)