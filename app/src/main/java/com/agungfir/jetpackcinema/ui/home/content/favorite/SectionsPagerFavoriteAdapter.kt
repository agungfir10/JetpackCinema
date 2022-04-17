package com.agungfir.jetpackcinema.ui.home.content.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.agungfir.jetpackcinema.R
import com.agungfir.jetpackcinema.ui.home.content.favorite.moviefavorite.MovieFavoriteFragment
import com.agungfir.jetpackcinema.ui.home.content.favorite.tvshowfavorite.TvShowFavoriteFragment

class SectionsPagerFavoriteAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie_fav, R.string.tv_show_fav)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFavoriteFragment()
            1 -> TvShowFavoriteFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(
        TAB_TITLES[position]
    )

    override fun getCount(): Int = 2

}