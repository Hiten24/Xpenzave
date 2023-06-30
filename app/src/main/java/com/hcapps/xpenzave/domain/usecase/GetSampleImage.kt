package com.hcapps.xpenzave.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetSampleImage @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun execute(): ByteArray {
        return context.assets.open("sample.jpeg").readAllBytes()
    }

}