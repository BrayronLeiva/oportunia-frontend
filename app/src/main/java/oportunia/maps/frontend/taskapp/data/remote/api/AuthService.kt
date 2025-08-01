package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.AuthRequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit service interface for authentication endpoints
 */
interface AuthService {
    /**
     * Authenticates a user with the provided credentials
     *
     * @param request Authentication credentials
     * @return Authentication response containing token
     */
    @POST("v1/users/login")
    suspend fun login(@Body request: AuthRequestDto): Response<AuthResponseDto>

    /**
     * Logs out the current user session
     */
    @POST("v1/users/logout") //TODO: Pending in backend
    suspend fun logout(): Response<Unit>
}