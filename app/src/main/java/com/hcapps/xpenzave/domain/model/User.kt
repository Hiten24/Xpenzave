package com.hcapps.xpenzave.domain.model

import com.hcapps.xpenzave.data.remote_source.repository.auth.AppUser

data class User(
    val userId: String = "",
    val email: String? = null,
    val name: String? = null,
    val currencyCode: String? = null
)

fun AppUser.toUser() = User(
    userId = id,
    email = email,
    name = name,
    currencyCode = null
)