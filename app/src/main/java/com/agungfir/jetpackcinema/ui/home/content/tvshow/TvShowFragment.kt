package com.agungfir.jetpackcinema.ui.home.content.tvshow

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.agungfir.jetpackcinema.data.source.local.entity.TvShowEntity
import com.agungfir.jetpackcinema.databinding.FragmentTvShowBinding
import com.agungfir.jetpackcinema.ui.detail.DetailActivity
import com.agungfir.jetpackcinema.ui.home.HomeViewModel
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import com.agungfir.jetpackcinema.vo.Status
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TvShowFragment : DaggerFragment(), TvShowCallback {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater)
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

        homeViewModel.getDiscoverTvShows().observe(viewLifecycleOwner) { listTvShow ->
            when (listTvShow.status) {
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
                    val tvShowAdapter = TvShowAdapter(this)
                    tvShowAdapter.submitList(listTvShow.data)

                    with(binding.rvTvShow) {
                        val orientation = resources.configuration.orientation
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            // In landscape
                            layoutManager = GridLayoutManager(context, 3)
                        } else {
                            // In portrait
                            layoutManager = GridLayoutManager(context, 2)
                        }
                        setHasFixedSize(true)
                        adapter = tvShowAdapter
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}