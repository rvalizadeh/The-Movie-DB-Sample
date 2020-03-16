package com.themoviedb.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.R
import com.themoviedb.base.BaseActivity
import com.themoviedb.databinding.ActivityMainBinding
import com.themoviedb.model.Results
import com.themoviedb.viewmodel.NowPlayingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val viewModel: NowPlayingViewModel by viewModel()
    private var PAGE_START = 1
    private var TOTAL_PAGES = 10
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = PAGE_START
    private var list: MutableList<Results> = ArrayList()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.nowPlayingResponseLiveData.observe(this, Observer {
            binding.progress.visibility = View.GONE
            list.addAll(it!!.results)
            when (currentPage == PAGE_START) {
                true -> {
                    setupAdapter()
                    setupTextChangeListener()
                }
                false -> (rc.adapter as NowPlayingAdapter).addAll(it!!.results)
            }

            isLoading = false
            TOTAL_PAGES = it!!.total_pages
            (rc.adapter as NowPlayingAdapter).removeLoadingFooter()

        })

        viewModel.errorMessageLiveData.observe(this, Observer {
            binding.progress.visibility = View.GONE
        })

    }

    private fun loadNextPage() {
        viewModel.getNowPlayingData(currentPage)
        (rc.adapter as NowPlayingAdapter).addLoadingFooter()
    }

    private fun setupAdapter() {

        binding.rc.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = NowPlayingAdapter(this@MainActivity, list)
            setScrollListener()
        }
    }

    private fun setScrollListener() {

        binding.rc.apply {

            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as LinearLayoutManager) {

                override fun loadMoreItems() {
                    isLoading = true
                    currentPage += 1
                    loadNextPage()
                }

                override fun getTotalPageCount(): Int {
                    return TOTAL_PAGES
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

            })
        }
    }

    private fun removeScrollListener(){
        binding.rc.clearOnScrollListeners()
    }


    private fun setupTextChangeListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when (binding.etSearch.text?.isEmpty()) {
                    true -> setScrollListener()
                    else -> removeScrollListener()
                }
                (rc.adapter as NowPlayingAdapter).filter.filter(binding.etSearch.text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

}
