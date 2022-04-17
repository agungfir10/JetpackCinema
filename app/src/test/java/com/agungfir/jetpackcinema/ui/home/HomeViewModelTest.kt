package com.agungfir.jetpackcinema.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.agungfir.jetpackcinema.data.source.CatalogRepository
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.agungfir.jetpackcinema.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerTvShow: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(catalogRepository)
    }

    @Test
    fun getDiscoverMovies() {
        val dummyMovie = Resource.success(moviePagedList)
        `when`(dummyMovie.data?.size).thenReturn(5)
        val movie = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movie.value = dummyMovie

        `when`(catalogRepository.getNowPlayingMovies()).thenReturn(movie)
        val movieEntity = viewModel.getDiscoverMovies().value?.data
        verify(catalogRepository).getNowPlayingMovies()
        assertNotNull(movieEntity)
        assertEquals(5, movieEntity?.size)

        viewModel.getDiscoverMovies().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getDiscoverTvShows() {
        val dummyTvShow = Resource.success(tvShowPagedList)
        `when`(dummyTvShow.data?.size).thenReturn(5)
        val tvShow = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShow.value = dummyTvShow

        `when`(catalogRepository.getTvShowOnTheAir()).thenReturn(tvShow)
        val tvShowEntity = viewModel.getDiscoverTvShows().value?.data
        verify(catalogRepository).getTvShowOnTheAir()
        assertNotNull(tvShowEntity)
        assertEquals(5, tvShowEntity?.size)

        viewModel.getDiscoverTvShows().observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyTvShow)
    }
}