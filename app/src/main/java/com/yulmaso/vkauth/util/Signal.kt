package com.yulmaso.vkauth.util

import android.util.ArraySet

/**Упаковка для команды*/
class Signal (
    val command: String,
    val signature: String,
    val flags: ArraySet<Int>
) {
    companion object{
        const val FLAG_REQUIRES_LOADING = 0
        const val FLAG_CAUSES_NAVIGATION = 1
    }
}