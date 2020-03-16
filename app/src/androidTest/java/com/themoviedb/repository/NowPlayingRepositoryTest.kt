package com.themoviedb.repository

import com.themoviedb.model.NowPlayingResponse
import com.themoviedb.model.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class NowPlayingRepositoryTest : KoinTest {


    val movieRepository: MovieRepository by inject()

    @Test
    fun viewModelTest() {

        runBlocking {

            val response : ResultWrapper<NowPlayingResponse> = movieRepository.nowPlayingData(1)
            assertTrue(response is ResultWrapper.Success)
            assertTrue((response as ResultWrapper.Success).value.total_pages > 0)
            assertTrue(response.value.results.size > 0)

        }

    }

}