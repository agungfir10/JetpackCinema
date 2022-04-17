package com.agungfir.jetpackcinema.ui.home.content.favorite.moviefavorite

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.databinding.FragmentMovieFavoriteBinding
import com.agungfir.jetpackcinema.ui.detail.DetailActivity
import com.agungfir.jetpackcinema.ui.home.content.favorite.FavoriteViewModel
import com.agungfir.jetpackcinema.ui.home.content.movie.MovieAdapter
import com.agungfir.jetpackcinema.ui.home.content.movie.MovieCallback
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MovieFavoriteFragment : DaggerFragment(), MovieCallback {

    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteView: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            favoriteView =
                ViewModelProvider(requireActivity(), factory)[FavoriteViewModel::class.java]
            favoriteView.getListFavoriteMovie().observe(viewLifecycleOwner) { listMovie ->
                val adapterMovie = MovieAdapter(this)
                adapterMovie.submitList(listMovie)
                adapterMovie.notifyDataSetChanged()
                with(binding.rvFavoriteMovie) {
                    val orientation = resources.configuration.orientation
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // In landscape
                        layoutManager = GridLayoutManager(context, 3)
                    } else {
                        // In portrait
                        layoutManager = GridLayoutManager(context, 2)
                    }
                    setHasFixedSize(true)
                    adapter = adapterMovie
                }
            }
        }
    }

    override fun onItemClicked(data: MovieEntity) {
        startActivity(
            Intent(context, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_MOVIE, data.movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}