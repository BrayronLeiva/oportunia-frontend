package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.QualificationService
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import retrofit2.Response
import javax.inject.Inject


/**
 * Remote data source for location company operations.
 * Handles all network operations related to location companies using [qualificationService].
 *
 * @property qualificationService The service interface for location company API calls
 */
class QualificationRemoteDataSource @Inject constructor(
    private val qualificationService: QualificationService
) {
    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getAll(): Result<List<QualificationDto>> = safeApiCall {
        qualificationService.getAllqualifications()
    }

    /**
     * Retrieves a specific location company by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getById(id: Long): Result<QualificationDto> = safeApiCall {
        qualificationService.getQualificationById(id)
    }

    /**
     * Creates a new location company.
     *
     * @param dto The location company data to create
     * @return [Result] containing the created [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: QualificationDto): Result<QualificationDto> = safeApiCall {
        qualificationService.createQualification(dto)
    }

    /**
     * Updates an existing location company.
     *
     * @param id The unique identifier of the location company to update
     * @param dto The updated location company data
     * @return [Result] containing the updated [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun update(id: Long, dto: QualificationDto): Result<QualificationDto> = safeApiCall {
        qualificationService.updateQualification(id, dto)
    }

    /**
     * Deletes a location company by its ID.
     *
     * @param id The unique identifier of the location company to delete
     * @return [Result] with success or failure
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        qualificationService.deleteQualification(id)
    }

    /**
     * Helper function to handle API calls safely.
     *
     * @param apiCall The suspending function making the API call
     * @return [Result] containing the data if successful, or an exception if failed
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}