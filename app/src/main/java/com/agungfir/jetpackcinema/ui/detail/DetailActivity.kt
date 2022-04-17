package com.agungfir.jetpackcinema.ui.detail

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.agungfir.jetpackcinema.BuildConfig
import com.agungfir.jetpackcinema.R
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.agungfir.jetpackcinema.databinding.ActivityDetailBinding
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        menuToolbarDetailFavorite()

        detailViewModel =
            ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        if (intent.getIntExtra(EXTRA_MOVIE, 0) != 0) {
            val idMovie = intent.getIntExtra(EXTRA_MOVIE, 0)
            detailViewModel.getMovieDetail(idMovie).observe(this) { movie ->
                setFavoriteState(movie.isFavorite)
                movie.title?.let {
                    collapsing_toolbar.title = it
                    detail_toolbar.title = it
                    tv_title_detail.text = it
                }
                tv_overview_detail.text = movie.overview
                tv_release_date_detail.text = movie.release_date
                rating_bar.rating = movie.vote_average!!.div(2F)
                Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions())
                    .load(BuildConfig.IMAGE_URL + movie.poster_path)
                    .into(binding.ivBackdropDetail)

                Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions())
                    .load(BuildConfig.IMAGE_URL + movie.poster_path)
                    .into(binding.detailContent.ivPosterDetail)
                fab_favorite.setOnClickListener {
                    setFavoriteMovie(movie)
                }

            }
        }
        if (intent.getIntExtra(EXTRA_TV_SHOW, 0) != 0) {
            val idTvShow = intent.getIntExtra(EXTRA_TV_SHOW, 0)
            detailViewModel.getTvShowDetail(idTvShow).observe(this) { tvshow ->
                setFavoriteState(tvshow.isFavorite)
                tvshow.name?.let {
                    collapsing_toolbar.title = it
                    detail_toolbar.title = it
                    tv_title_detail.text = it
                }
                tv_overview_detail.text = tvshow.overview
                tv_release_date_detail.text = tvshow.first_air_date
                rating_bar.rating = tvshow.vote_average!!.div(2)
                Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions())
                    .load(BuildConfig.IMAGE_URL + tvshow.poster_path)
                    .into(binding.ivBackdropDetail)
                Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions())
                    .load(BuildConfig.IMAGE_URL + tvshow.poster_path)
                    .into(binding.detailContent.ivPosterDetail)
                fab_favorite.setOnClickListener {
                    setFavoriteTvShow(tvshow)
                }

            }
        }

        changeHeightAppbarlayoutInLandscape()
    }

    private fun changeHeightAppbarlayoutInLandscape() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            binding.appBarLayoutDetail.layoutParams.height = 400
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun showSnackBar(message: String, color: Int = Color.BLACK) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(color)
        snackbar.show()
    }

    private fun setFavoriteMovie(movie: MovieEntity) {
        if (movie.isFavorite) {
            showSnackBar("Removed from favorite movie", Color.rgb(223, 1, 89))
            detailViewModel.setFavoriteMovie(movie)
        } else {
            showSnackBar("Added from favorite movie", Color.rgb(66, 186, 150))
            detailViewModel.setFavoriteMovie(movie)
        }
    }

    private fun setFavoriteTvShow(tvshow: TvShowEntity) {
        if (tvshow.isFavorite) {
            showSnackBar("Removed from favorite tv show", Color.rgb(223, 1, 89))
            detailViewModel.setFavoriteTvShow(tvshow)
        } else {
            showSnackBar("Added from favorite movie", Color.rgb(66, 186, 150))
            detailViewModel.setFavoriteTvShow(tvshow)
        }
    }

    private fun setFavoriteState(status: Boolean) {
        if (status) {
            fab_favorite.setImageResource(R.drawable.ic_favorited_pink)
            binding.detailToolbar.menu?.findItem(R.id.action_favorite)
                ?.setIcon(R.drawable.ic_favorited_pink)
        } else {
            fab_favorite.setImageResource(R.drawable.ic_favorite_pink)
            binding.detailToolbar.menu?.findItem(R.id.action_favorite)
                ?.setIcon(R.drawable.ic_favorite_pink)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun menuToolbarDetailFavorite() {
        app_bar_layout_detail.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val toolbarHeight = detail_toolbar.measuredHeight
                val appBarHeight = appBarLayout.measuredHeight
                val alpha =
                    ((appBarHeight.toFloat() - toolbarHeight) + verticalOffset) / (appBarHeight - toolbarHeight) * 255
                detail_toolbar?.menu?.findItem(R.id.action_favorite)?.icon?.alpha =
                    255 - Math.round(alpha)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        if (intent.getIntExtra(EXTRA_MOVIE, 0) != 0) {
            val idMovie = intent.getIntExtra(EXTRA_MOVIE, 0)
            detailViewModel.getMovieDetail(idMovie).observe(this) { movie ->
                menu?.findItem(R.id.action_favorite)
                    ?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            setFavoriteMovie(movie)
                            return true
                        }
                    })
                setFavoriteState(movie.isFavorite)
            }
        } else {
            val idTvShow = intent.getIntExtra(EXTRA_TV_SHOW, 0)
            detailViewModel.getTvShowDetail(idTvShow).observe(this) { tvshow ->
                menu?.findItem(R.id.action_favorite)
                    ?.setOnMenuItemClickListener {
                        setFavoriteTvShow(tvshow)
                        true
                    }
                setFavoriteState(tvshow.isFavorite)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (intent.getIntExtra(EXTRA_MOVIE, 0) != 0) {
            val idMovie = intent.getIntExtra(EXTRA_MOVIE, 0)
            detailViewModel.getMovieDetail(idMovie).observe(this) { movie ->
                if (item.itemId == R.id.action_favorite) {
                    setFavoriteMovie(movie)
                }
            }
        } else {
            val idTvShow = intent.getIntExtra(EXTRA_TV_SHOW, 0)
            detailViewModel.getTvShowDetail(idTvShow).observe(this) { tvshow ->
                if (item.itemId == R.id.action_favorite) {
                    setFavoriteTvShow(tvshow)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }


}