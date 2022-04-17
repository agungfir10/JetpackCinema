package com.agungfir.jetpackcinema.di.home

import com.agungfir.jetpackcinema.di.home.favorite.FavoriteFragmentBuildersModule
import com.agungfir.jetpackcinema.ui.home.content.favorite.FavoriteFragment
import com.agungfir.jetpackcinema.ui.home.content.movie.MovieFragment
import com.agungfir.jetpackcinema.ui.home.content.tvshow.TvShowFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeTvShowFragment(): TvShowFragment

    @ContributesAndroidInjector(modules = [FavoriteFragmentBuildersModule::class])
    abstract fun contributeFavoriteFragment(): FavoriteFragment
}