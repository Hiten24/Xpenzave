package com.hcapps.xpenzave.presentation.auth.event

data class PasswordState(
    val shouldBeMin8Max20Char: Boolean = false,
    val shouldHaveALowerCase: Boolean = false,
    val shouldHaveAUpperCase: Boolean = false,
    val shouldHaveANumberOrAcceptableCharacter: Boolean = false
)

val passwordRules = "Password should include:" +
        "\n Password should be 8-20 characters." +
        "\nPassword should have a lower case letter. " +
        "\nPassword should have a upper case letter. " +
        "\nPassword should have a number or acceptable character $ ! # & @ ? % = _"
