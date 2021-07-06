package com.erencol.igitrepo.api
import com.erencol.igitrepo.model.GitRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user:String): Single<List<GitRepo>>
}