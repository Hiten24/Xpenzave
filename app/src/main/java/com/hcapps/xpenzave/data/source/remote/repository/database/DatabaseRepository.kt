package com.hcapps.xpenzave.data.source.remote.repository.database

import com.hcapps.xpenzave.domain.model.CategoryDataResponse
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.domain.model.Response

typealias CategoryResponse = RequestState<List<Response<CategoryDataResponse>>>

interface DatabaseRepository {

    suspend fun getCategories(): CategoryResponse

}