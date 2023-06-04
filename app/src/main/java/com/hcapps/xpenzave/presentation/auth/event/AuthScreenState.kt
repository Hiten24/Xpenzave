package com.hcapps.xpenzave.presentation.auth.event

data class AuthScreenState(
    val authState: Int = 1,
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false
)
