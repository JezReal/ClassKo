package app.netlify.accessdeniedgc.classko.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ClassKoAuthApi {

    @POST("login")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<Unit>
}