package com.example.myapplication.api

import com.example.myapplication.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface NowPlayingService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhOGI0YWFjYTQzNGJmOWRkZjhkNzAwNDc3Nzk5ZGQ0NSIsInN1YiI6IjY0ZTc2ZjBjNTI1OGFlMDEyY2E0OGRmOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.21pJcF7ot5st64v_mrxgpRbTpCdPPXLuNKnqAefnA_s")
    @GET("3/movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): MovieResponse
}