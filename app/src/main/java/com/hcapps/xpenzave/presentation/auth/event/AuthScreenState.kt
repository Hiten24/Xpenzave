package com.hcapps.xpenzave.presentation.auth.event

data class AuthScreenState(
    val authState: Int = 1,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val loading: Boolean = false,
)
