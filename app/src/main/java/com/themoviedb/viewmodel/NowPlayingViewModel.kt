package com.themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import com.themoviedb.R
import com.themoviedb.base.BaseViewModel
import com.themoviedb.model.NowPlayingResponse
import com.themoviedb.model.ResultWrapper
import com.themoviedb.repository.MovieRepository

class NowPlayingViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    val nowPlayingResponseLiveData = MutableLiveData<NowPlayingResponse?>()

    init {
        getNowPlayingData(1)
    }

    fun getNowPlayingData(page: Int) {
        launchDataLoad {
            val response = movieRepository.nowPlayingData(page)
            when (response) {
                is ResultWrapper.Success -> {

                    val nowPlayingResponse : NowPlayingResponse = response.value
                    nowPlayingResponseLiveData.postValue(nowPlayingResponse)

                }
                is ResultWrapper.GenericError -> {
                    response.error?.message?.let {
                        errorMessageLiveData.postValue(it)
                    }
                }
                ResultWrapper.NetworkError -> {
                    errorMessageLiveData.postValue(R.string.failed_message)
                }
            }
        }
    }

}