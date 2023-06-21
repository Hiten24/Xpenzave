package com.hcapps.xpenzave.data.remote_source.repository.auth

import androidx.activity.ComponentActivity
import com.hcapps.xpenzave.domain.model.RequestState
import com.hcapps.xpenzave.util.Constant.OAUTH2_FAILED_SUFFIX
import com.hcapps.xpenzave.util.Constant.OAUTH2_REDIRECT_LINK
import com.hcapps.xpenzave.util.Constant.OAUTH2_SUCCESS_SUFFIX
import io.appwrite.ID
import io.appwrite.models.Session
import io.appwrite.models.User
import io.appwrite.services.Account
import timber.log.Timber
import javax.inject.Inject

typealias AppUser = User<Map<String, Any>>

class AuthRepositoryImpl @Inject constructor(
    private val account: Account
): AuthRepository {

    override suspend fun createAccountWithCredentials(
        email: String,
        password: String
    ): RequestState<AppUser> {
        return try {
            val response = account.create(
                userId = ID.unique(),
                email = email,
                password = password
            )
            Timber.i("response: $response")
            Timber.i("response: ${response.registration}")
            Timber.i("createAccountWithCredentials: user " + response.email + " registered successfully")
            RequestState.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e.message == "A user with the same email already exists in your project.") {
                RequestState.Error(Exception("A user with the same email already exists."))
            } else {
                RequestState.Error(e)
            }
        }
    }

    override suspend fun loginWithCredentials(
        email: String,
        password: String
    ): RequestState<Session> {
        return try {
            val response = account.createEmailSession(
                email = email,
                password = password
            )
            RequestState.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            RequestState.Error(e)
        }
    }

    override suspend fun authenticateWithOauth2(
        activity: ComponentActivity,
        provider: String
    ): RequestState<Boolean> {
        return try {
            account.createOAuth2Session(
                activity = activity,
                provider = provider,
                success = "${OAUTH2_REDIRECT_LINK}${OAUTH2_SUCCESS_SUFFIX}",
                failure = "${OAUTH2_REDIRECT_LINK}${OAUTH2_FAILED_SUFFIX}"
            )
            RequestState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            RequestState.Error(e)
        }
    }

    override suspend fun logOut(): RequestState<Boolean> {
        return try {
            // logs out from all the devices
            account.deleteSessions()
            RequestState.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            RequestState.Error(e)
        }
    }

    override suspend fun getAccount(): RequestState<AppUser> {
        return try {
            RequestState.Success(account.get())
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): RequestState<Boolean> {
        return try {
            account.updatePassword(newPassword, oldPassword)
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun passwordRecovery(email: String): RequestState<Boolean> {
        return try {
            val response = account.createRecovery(
                email = email,
                url = "https://localhost/reset-password"
            )
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun resetPassword(
        userId: String,
        secret: String,
        password: String,
        passwordAgain: String
    ): RequestState<Boolean> {
        return try {
            account.updateRecovery(
                userId = userId,
                secret = secret,
                password = password,
                passwordAgain = passwordAgain
            )
            RequestState.Success(true)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }
}