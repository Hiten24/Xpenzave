package com.hcapps.xpenzave.presentation.auth.event

data class PasswordState(
    val shouldBeMin8Max20Char: Boolean = false,
    val shouldHaveALowerCase: Boolean = false,
    val shouldHaveAUpperCase: Boolean = false,
    val shouldHaveANumberOrAcceptableCharacter: Boolean = false
) {
    fun validate() = shouldBeMin8Max20Char && shouldHaveALowerCase && shouldHaveAUpperCase && shouldHaveANumberOrAcceptableCharacter

    companion object {
        fun checkAllRule(pass: String?): PasswordState = PasswordState(
            shouldBeMin8Max20Char = pass?.let { shouldBeMin8Max20Char(it) } ?: false,
            shouldHaveALowerCase = pass?.let { shouldHaveALowerCase(it) } ?: false,
            shouldHaveAUpperCase = pass?.let { shouldHaveAUpperCase(it) } ?: false,
            shouldHaveANumberOrAcceptableCharacter = pass?.let {
                shouldHaveANumberOrAcceptableCharacter(
                    it
                )
            } ?: false
        )

        fun shouldBeMin8Max20Char(pass: String) = pass.length in 8..20
        fun shouldHaveALowerCase(pass: String) = Regex("[a-z]").containsMatchIn(pass)
        fun shouldHaveAUpperCase(pass: String) = Regex("[A-Z]").containsMatchIn(pass)
        fun shouldHaveANumberOrAcceptableCharacter(pass: String) =
            Regex("[1-9$!#&@?=_]").containsMatchIn(pass)
    }

}
