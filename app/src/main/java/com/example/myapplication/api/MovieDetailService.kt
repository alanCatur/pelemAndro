package com.example.myapplication.api

import com.example.myapplication.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieDetailService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhOGI0YWFjYTQzNGJmOWRkZjhkNzAwNDc3Nzk5ZGQ0NSIsInN1YiI6IjY0ZTc2ZjBjNTI1OGFlMDEyY2E0OGRmOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.21pJcF7ot5st64v_mrxgpRbTpCdPPXLuNKnqAefnA_s")
    @GET("3/movie/{movie_id}?language=en-US")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetail
}
