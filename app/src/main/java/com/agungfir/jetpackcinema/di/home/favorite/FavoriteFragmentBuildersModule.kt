package com.agungfir.jetpackcinema.di.home.favorite

import com.agungfir.jetpackcinema.ui.home.content.favorite.moviefavorite.MovieFavoriteFragment
import com.agungfir.jetpackcinema.ui.home.content.favorite.tvshowfavorite.TvShowFavoriteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment(): MovieFavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteTvShowFragment(): TvShowFavoriteFragment
}