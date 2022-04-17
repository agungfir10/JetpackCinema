package com.agungfir.jetpackcinema.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.agungfir.jetpackcinema.data.source.CatalogRepository
import com.agungfir.jetpackcinema.data.source.DataDummy
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.lenient
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private val dummyMovie = DataDummy.generateDummyResponseDiscoverMovies()[0]
    private val movieId = dummyMovie.movieId
    private val dummyTvShow = DataDummy.generateDummyResponseDiscoverTvShows()[0]
    private val tvShowId = dummyTvShow.tvShowId

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observerMovie: Observer<MovieEntity>

    @Mock
    private lateinit var observerTvShow: Observer<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(catalogRepository)
    }

    @Test
    fun getMovieDetail() {
        val movieDummy = MutableLiveData<MovieEntity>()
        movieDummy.value = dummyMovie

        `when`(catalogRepository.getMovieDetail(movieId)).thenReturn(movieDummy)

        val movieData = viewModel.getMovieDetail(movieId).value

        assertNotNull(movieData)
        assertEquals(dummyMovie.id, movieData?.id)
        assertEquals(dummyMovie.movieId, movieData?.movieId)
        assertEquals(dummyMovie.title, movieData?.title)
        assertEquals(dummyMovie.overview, movieData?.overview)
        assertEquals(dummyMovie.poster_path, movieData?.poster_path)
        assertEquals(dummyMovie.backdrop_path, movieData?.backdrop_path)
        assertEquals(dummyMovie.release_date, movieData?.release_date)

        viewModel.getMovieDetail(movieId).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)

    }

    @Test
    fun getTvShowDetail() {
        val tvShowDummy = MutableLiveData<TvShowEntity>()
        tvShowDummy.value = dummyTvShow

        `when`(catalogRepository.getTvShowDetail(tvShowId)).thenReturn(tvShowDummy)

        val tvShowData = viewModel.getTvShowDetail(tvShowId).value

        assertNotNull(tvShowData)
        assertEquals(dummyTvShow.id, tvShowData?.id)
        assertEquals(dummyTvShow.tvShowId, tvShowData?.tvShowId)
        assertEquals(dummyTvShow.name, tvShowData?.name)
        assertEquals(dummyTvShow.overview, tvShowData?.overview)
        assertEquals(dummyTvShow.poster_path, tvShowData?.poster_path)
        assertEquals(dummyTvShow.backdrop_path, tvShowData?.backdrop_path)
        assertEquals(dummyTvShow.first_air_date, tvShowData?.first_air_date)

        viewModel.getTvShowDetail(tvShowId).observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteMovie() {
        val movieEntity = MovieEntity(
            id = 1,
            movieId = 464052,
            backdrop_path = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
            overview = "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
            poster_path = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
            release_date = "2020-12-16",
            title = "Wonder Woman 1984",
        )
        lenient().doNothing().`when`(catalogRepository).setFavoriteMovie(movieEntity)
        assertEquals(dummyMovie, movieEntity)

    }

    @Test
    fun setFavoriteTvShow() {
        val tvShowEntity = TvShowEntity(
            id = 1,
            backdrop_path = "/57vVjteucIF3bGnZj6PmaoJRScw.jpg",
            first_air_date = "2021-01-15",
            tvShowId = 85271,
            overview = "Wanda Maximoff and Vision—two super-powered beings living idealized suburban lives—begin to suspect that everything is not as it seems.",
            poster_path = "/glKDfE6btIRcVB5zrjspRIs4r52.jpg",
        )
        lenient().doNothing().`when`(catalogRepository).setFavoriteTvShow(tvShowEntity)
        assertEquals(dummyTvShow, tvShowEntity)

    }

}