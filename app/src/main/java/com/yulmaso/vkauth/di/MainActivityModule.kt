package com.yulmaso.vkauth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yulmaso.vkauth.ui.MainActivity
import com.yulmaso.vkauth.ui.MainActivityViewModel
import com.yulmaso.vkauth.ui.auth.AuthFragment
import com.yulmaso.vkauth.ui.auth.AuthViewModel
import com.yulmaso.vkauth.ui.page.PageFragment
import com.yulmaso.vkauth.ui.page.PageViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesAuthFragment(): AuthFragment

    @ContributesAndroidInjector
    abstract fun contributesPageFragment(): PageFragment

}