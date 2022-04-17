package com.agungfir.jetpackcinema.di

import com.agungfir.jetpackcinema.di.home.HomeFragmentBuildersModule
import com.agungfir.jetpackcinema.ui.detail.DetailActivity
import com.agungfir.jetpackcinema.ui.home.HomeActivity
import com.agungfir.jetpackcinema.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

}