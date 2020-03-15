package com.themoviedb

import android.graphics.Movie
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.base.BaseActivity
import com.themoviedb.viewmodel.NowPlayingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val viewModel: NowPlayingViewModel by viewModel()

    // Index from which pagination should start (0 is 1st page in our case)
    private val PAGE_START = 0

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private var isLoading = false

    // If current page is the last page (Pagination will stop after this page load)
    private var isLastPage = false

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private val TOTAL_PAGES = 3

    // indicates the current page which Pagination is fetching.
    private var currentPage = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.nowPlayingResponseLiveData.observe(this , Observer {
            var linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rc.setLayoutManager(linearLayoutManager)
            rc.setItemAnimator(DefaultItemAnimator())

            val adapter = NowPlayingAdapter(this , it!!.results)
            rc.setAdapter(adapter)

            rc.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun loadMoreItems() {
                    isLoading = true
                    //Increment page index to load the next one
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
        })

        viewModel.errorMessageLiveData.observe(this , Observer {
            var dd = it
        })


    }

    private fun loadFirstPage() { // fetching dummy data

      //  progressBar.setVisibility(View.GONE)
     //   adapter.addAll(movies)
     //   if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter() else isLastPage = true
    }

    private fun loadNextPage() {
        viewModel.getNowPlayingData(currentPage)
       // val movies: List<Movie> = Movie.createMovies(adapter.getItemCount()) // 1
       // adapter.removeLoadingFooter() // 2
      //  isLoading = false // 3
      ///  adapter.addAll(movies) // 4
       // if (currentPage !== TOTAL_PAGES) adapter.addLoadingFooter() // 5
       // else isLastPage = true
    }
}
