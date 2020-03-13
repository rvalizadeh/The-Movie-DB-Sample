package com.themoviedb

import android.os.Bundle
import androidx.lifecycle.Observer
import com.themoviedb.base.BaseActivity
import com.themoviedb.viewmodel.NowPlayingViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: NowPlayingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.nowPlayingResponseLiveData.observe(this , Observer {
            var dd = it
        })

        viewModel.errorMessageLiveData.observe(this , Observer {
            var dd = it
        })


    }
}
