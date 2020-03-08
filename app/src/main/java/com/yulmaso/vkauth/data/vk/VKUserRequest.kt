package com.yulmaso.vkauth.data.vk

import com.vk.api.sdk.requests.VKRequest
import com.yulmaso.vkauth.data.vk.VKUser
import org.json.JSONObject

class VKUserRequest(uid: Int): VKRequest<VKUser>("users.get") {
    init {
        if (uid != 0) {
            addParam("user_id", uid)
        }
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): VKUser {
        val users = r.getJSONArray("response")
        return VKUser.parse(users.getJSONObject(0))
    }
}