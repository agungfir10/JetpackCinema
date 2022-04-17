package com.agungfir.jetpackcinema.ui.home.content.movie

import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity

interface MovieCallback {
    fun onItemClicked(data: MovieEntity)
}