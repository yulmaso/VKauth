package com.yulmaso.vkauth

import com.vk.api.sdk.VK
import com.yulmaso.vkauth.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().application(this).build();

    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
    }

}