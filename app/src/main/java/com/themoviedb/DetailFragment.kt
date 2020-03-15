package com.themoviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.themoviedb.base.BaseFragment
import com.themoviedb.databinding.FragmentMovieDetailBinding
import com.themoviedb.models.Results


class DetailFragment : BaseFragment() {

    lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        var results = arguments!!.get(DataConstant.RESULT_KEY) as Results
        binding.results = results
        binding.progress = (results.vote_average * 10).toInt()
        binding.releaseDate = resources.getString(R.string.release_date) + results.release_date
        Glide.with(context!!).load(DataConstant.IMAGE_BASE_URL + results.backdrop_path)
            .into(binding.backgroundImg)
        return binding.root
    }

}