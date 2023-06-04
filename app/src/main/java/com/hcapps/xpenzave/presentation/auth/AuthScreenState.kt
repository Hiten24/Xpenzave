package com.hcapps.xpenzave.presentation.auth

data class AuthScreenState(
    val authState: Int = 1,
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false
)
