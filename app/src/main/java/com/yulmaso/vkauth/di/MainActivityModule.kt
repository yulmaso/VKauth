package com.yulmaso.vkauth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yulmaso.vkauth.ui.MainActivity
import com.yulmaso.vkauth.ui.auth.AuthFragment
import com.yulmaso.vkauth.ui.auth.AuthViewModel
import com.yulmaso.vkauth.ui.page.PageFragment
import com.yulmaso.vkauth.ui.page.PageViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesAuthFragment(): AuthFragment

    @ContributesAndroidInjector
    abstract fun contributesPageFragment(): PageFragment

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: AuthViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PageViewModel::class)
    abstract fun bindCalendarViewModel(calendarViewModel: PageViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

}