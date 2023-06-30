package com.hcapps.xpenzave.util

import com.hcapps.xpenzave.BuildConfig

object Constant {

    const val APP_WRITE_ENDPOINT = BuildConfig.endpoint
    const val APP_WRITE_PROJECT_ID = BuildConfig.projectId

    const val APP_WRITE_DATABASE_ID = BuildConfig.databaseId

    const val APP_WRITE_CATEGORY_COLLECTION_ID = BuildConfig.categoryCollectionId
    const val APP_WRITE_BUDGE_COLLECTION_ID = BuildConfig.budgetCollectionId
    const val APP_WRITE_EXPENSE_COLLECTION_ID = BuildConfig.expenseCollectionId

    const val APP_WRITE_EXPENSE_PHOTO_BUCKET_IT = BuildConfig.photoBucketId

    const val AUTH_LOGIN_SCREEN = 1
    const val AUTH_REGISTER_SCREEN = 2

//     OAuth2
    const val OAUTH2_REDIRECT_LINK = "appwrite-callback-${APP_WRITE_PROJECT_ID}://localhost/authentication/"
    const val OAUTH2_SUCCESS_SUFFIX = "verify"
    const val OAUTH2_FAILED_SUFFIX = "failed"


}