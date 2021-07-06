package com.erencol.igitrepo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erencol.igitrepo.api.ApiRequest
import com.erencol.igitrepo.model.GitRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class GitRepoViewModel: ViewModel() {

    private val apiRequest= ApiRequest()
    private val disposable= CompositeDisposable()
    val gitRepoList = MutableLiveData<List<GitRepo>>()
    val inProgress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getGitRepos(user: String){
        inProgress.value = true
        error.value = ""
        disposable.add(
            apiRequest.getRepos(user).
            subscribeOn(Schedulers.newThread()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribeWith(object: DisposableSingleObserver<List<GitRepo>>() {
                override fun onSuccess(value: List<GitRepo>?) {
                    gitRepoList.value = value
                    inProgress.value = false
                }

                override fun onError(e: Throwable?) {
                    if (e != null) {
                        error.value = e.message
                    }
                    inProgress.value = false

                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}