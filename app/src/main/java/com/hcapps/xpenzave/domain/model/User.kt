package com.hcapps.xpenzave.domain.model

data class User(
    val userId: String = "",
    val email: String? = null,
    val currencyCode: String? = null
)
