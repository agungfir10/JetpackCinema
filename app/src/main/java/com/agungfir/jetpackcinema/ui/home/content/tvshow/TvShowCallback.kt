package com.agungfir.jetpackcinema.ui.home.content.tvshow

import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity

interface TvShowCallback {
    fun onItemClicked(data: TvShowEntity)
}