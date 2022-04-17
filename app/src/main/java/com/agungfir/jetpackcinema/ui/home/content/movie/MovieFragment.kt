package com.agungfir.jetpackcinema.ui.home.content.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.agungfir.jetpackcinema.data.source.local.entity.MovieEntity
import com.agungfir.jetpackcinema.databinding.FragmentMovieBinding
import com.agungfir.jetpackcinema.ui.detail.DetailActivity
import com.agungfir.jetpackcinema.ui.home.HomeViewModel
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import com.agungfir.jetpackcinema.vo.Status
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieFragment : DaggerFragment(), MovieCallback {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            setUpViewModelAndObserve()
        }
    }

    private fun setUpViewModelAndObserve() {
        homeViewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]

        homeViewModel.getDiscoverMovies().observe(viewLifecycleOwner) { listMovies ->
            when (listMovies.status) {
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
                    val movieAdapter = MovieAdapter(this)
                    movieAdapter.submitList(listMovies.data)
                    with(binding.rvMovie) {
                        val orientation = resources.configuration.orientation
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            // In landscape
                            layoutManager = GridLayoutManager(context, 3)
                        } else {
                            // In portrait
                            layoutManager = GridLayoutManager(context, 2)
                        }
                        setHasFixedSize(true)
                        adapter = movieAdapter
                    }
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