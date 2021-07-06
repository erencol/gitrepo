package com.erencol.igitrepo.di

import com.erencol.igitrepo.api.ApiRequest
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(apiRequest: ApiRequest)
}