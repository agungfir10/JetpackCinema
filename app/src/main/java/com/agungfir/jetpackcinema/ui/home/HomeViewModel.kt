package com.agungfir.jetpackcinema.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.agungfir.jetpackcinema.data.source.CatalogRepository
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.agungfir.jetpackcinema.vo.Resource
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val catalogRepository: CatalogRepository) :
    ViewModel() {

    fun getDiscoverMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        catalogRepository.getNowPlayingMovies()

    fun getDiscoverTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> =
        catalogRepository.getTvShowOnTheAir()

}