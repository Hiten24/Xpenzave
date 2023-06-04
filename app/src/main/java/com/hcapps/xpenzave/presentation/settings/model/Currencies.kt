package com.hcapps.xpenzave.presentation.settings.model

import com.google.gson.annotations.SerializedName

data class Currency(
    val symbol: String,
    val name: String,
    @SerializedName("symbol_native")
    val symbolNative: String,
    @SerializedName("decimal_digits")
    val decimalDigits: Int,
    val code: String,
    @SerializedName("name_plurals")
    val namePlurals: String
)
