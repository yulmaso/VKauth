package com.yulmaso.vkauth.util


/**commands a view model shall ever need to emit to its fragment*/
object Commands {

    //to Auth Fragment
    const val VK_LOGIN = "VK_LOGIN"
    const val NAVIGATE_UP = "NAVIGATE_UP"
    const val REPORT_AUTH_FAIL = "REPORT_AUTH_FAIL"

    //to Main Fragment
    const val NAVIGATE_TO_AUTH = "NAVIGATE_TO_AUTH"
    const val REPORT_REQUEST_FAIL = "REPORT_REQUEST_FAIL"
    const val VK_LOGOUT = "VK_LOGOUT"

    //vm states
    const val LOAD = "LOAD"
    const val STOP_LOADING = "STOP_LOADING"
    const val SIT_IDLE = "SIT_IDLE"

}