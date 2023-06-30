package com.hcapps.xpenzave.data.remote_source.repository.appwrite

import androidx.annotation.Keep
import com.hcapps.xpenzave.domain.model.User
import io.appwrite.Permission
import io.appwrite.Role

@Keep
object AppWriteUtil {
    fun permissions(user: User): List<String> {
        return listOf(
            Permission.read(Role.user(user.userId)),
            Permission.update(Role.user(user.userId)),
            Permission.delete(Role.user(user.userId)),
        )
    }
}