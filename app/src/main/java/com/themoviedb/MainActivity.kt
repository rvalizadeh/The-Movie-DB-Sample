package com.themoviedb

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.base.BaseActivity
import com.themoviedb.databinding.ActivityMainBinding
import com.themoviedb.models.Results
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

            list.addAll(it!!.results)

            when (currentPage == PAGE_START) {
                true -> setupAdapter()
                false -> (rc.adapter as NowPlayingAdapter).addAll(it!!.results)
            }

            isLoading = false
            TOTAL_PAGES = it!!.total_pages
            (rc.adapter as NowPlayingAdapter).removeLoadingFooter()

        })

        viewModel.errorMessageLiveData.observe(this, Observer {

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
}
