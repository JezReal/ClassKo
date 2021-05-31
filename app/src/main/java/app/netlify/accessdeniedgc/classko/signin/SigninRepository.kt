package app.netlify.accessdeniedgc.classko.signin

import app.netlify.accessdeniedgc.classko.network.AuthRequest
import app.netlify.accessdeniedgc.classko.network.ClassKoApi
import app.netlify.accessdeniedgc.classko.network.ClassKoAuthApi
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class SigninRepository @Inject constructor(
    private val api: ClassKoAuthApi
) {

    suspend fun authenticate(username: String, password: String) : Resource<String> {
        try {
            val response = api.authenticate(AuthRequest(username, password))
            val headers = response.headers()

            if (response.isSuccessful) {
                val auth = headers.get("Authorization")
                Timber.d("Header auth: $auth")
                return Resource.Success(auth!!)
            }
        } catch (e : UnknownHostException) {
            return Resource.Failure("No connection")
        }

        return Resource.Failure("Invalid credentials")
    }
}