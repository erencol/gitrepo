package com.erencol.igitrepo.api

import com.erencol.igitrepo.di.DaggerApiComponent
import com.erencol.igitrepo.model.GitRepo
import io.reactivex.Single
import javax.inject.Inject

class ApiRequest() {

    @Inject
    lateinit var api: Api

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getRepos(user: String): Single<List<GitRepo>>{
        return api.getRepos(user)
    }



}