package com.yulmaso.vkauth.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.yulmaso.vkauth.util.AUTH_PREFERENCES
import dagger.Module
import dagger.Provides

@Module
class AppModule{

    @Provides
    fun provideAuthPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(AUTH_PREFERENCES, MODE_PRIVATE)

}