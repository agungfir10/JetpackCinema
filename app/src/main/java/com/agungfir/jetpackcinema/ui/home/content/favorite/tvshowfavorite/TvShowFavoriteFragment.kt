package com.agungfir.jetpackcinema.ui.home.content.favorite.tvshowfavorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.agungfir.jetpackcinema.databinding.FragmentTvShowFavoriteBinding
import com.agungfir.jetpackcinema.ui.detail.DetailActivity
import com.agungfir.jetpackcinema.ui.home.content.favorite.FavoriteViewModel
import com.agungfir.jetpackcinema.ui.home.content.tvshow.TvShowAdapter
import com.agungfir.jetpackcinema.ui.home.content.tvshow.TvShowCallback
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TvShowFavoriteFragment : DaggerFragment(), TvShowCallback {

    private lateinit var binding: FragmentTvShowFavoriteBinding
    private lateinit var favoriteView: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            favoriteView =
                ViewModelProvider(requireActivity(), factory)[FavoriteViewModel::class.java]
            favoriteView.getListFavoriteTvShow().observe(viewLifecycleOwner) { listTvShow ->
                val adapterTvShow = TvShowAdapter(this)
                adapterTvShow.submitList(listTvShow)
                adapterTvShow.notifyDataSetChanged()
                with(binding.rvFavoriteTvShow) {
                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        layoutManager = GridLayoutManager(context, 3)
                    } else {
                        // In portrait
                        layoutManager = GridLayoutManager(context, 2)
                    }
                    setHasFixedSize(true)
                    adapter = adapterTvShow
                }
            }
        }
    }

    override fun onItemClicked(data: TvShowEntity) {
        startActivity(
            Intent(context, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_TV_SHOW, data.tvShowId)
        )
    }
}