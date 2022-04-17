package com.agungfir.jetpackcinema.ui.home.content.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.agungfir.jetpackcinema.databinding.FragmentFavoriteBinding
import com.agungfir.jetpackcinema.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            setUpTabsAndViewPager(it)
        }

        favoriteViewModel =
            ViewModelProvider(this@FavoriteFragment, factory)[FavoriteViewModel::class.java]

    }

    private fun setUpTabsAndViewPager(context: Context) {
        val sectionsPagerFavoriteAdapter =
            SectionsPagerFavoriteAdapter(context, childFragmentManager)
        binding.viewPagerFavorite.adapter = sectionsPagerFavoriteAdapter
        binding.tabsFavorite.setupWithViewPager(view_pager_favorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}