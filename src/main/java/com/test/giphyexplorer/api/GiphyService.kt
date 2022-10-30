package com.test.giphyexplorer.api

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("search")
    suspend fun search(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String,
        @Query("limit") pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("offset") page: Int,
        @Query("lang") lang: String = DEFAULT_LANGUAGE
    ): GiphyResponse

    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/gifs/"

        const val DEFAULT_PAGE_SIZE = 30
        const val DEFAULT_LANGUAGE = "en"

        /*
        In real world production apps, it's a bad idea to include api keys into version control.
        It should be stored locally on build machine.
        */
        private const val API_KEY = "VYsyOfbUQhxEShZr4ueolDx2mbo7l2em"
    }
}