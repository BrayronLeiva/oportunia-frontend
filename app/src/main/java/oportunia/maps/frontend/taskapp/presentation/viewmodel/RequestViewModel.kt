package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestUpdateDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.model.Request
import oportunia.maps.frontend.taskapp.domain.repository.RequestRepository
import javax.inject.Inject

sealed class RequestState {
    /** Indicates an ongoing internship operation */
    data object Loading : RequestState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val internships: List<Request>) : RequestState()

    /** Indicates no internships are available */
    data object Empty : RequestState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : RequestState()
}

sealed class RequestDeleteState{
    /** Indicates an ongoing internship operation */
    data object Loading : RequestDeleteState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val request: Unit) : RequestDeleteState()

    /** Indicates no internships are available */
    data object Empty : RequestDeleteState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : RequestDeleteState()
}

sealed class RequestCreateState{
    /** Indicates an ongoing internship operation */
    data object Loading : RequestCreateState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val request: Request) : RequestCreateState()

    /** Indicates no internships are available */
    data object Empty : RequestCreateState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : RequestCreateState()
}


sealed class RequestUpdateState{
    /** Indicates an ongoing internship operation */
    data object Loading : RequestUpdateState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val request: Request) : RequestUpdateState()

    /** Indicates no internships are available */
    data object Empty : RequestUpdateState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : RequestUpdateState()
}

/**
 * ViewModel responsible for managing location and internship-related UI state and business logic.
 *
 * @property locationCompanyRepository Repository interface for location operations
 * @property internshipLocationRepository Repository interface for internship-related operations
 */
@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestRepository: RequestRepository
) : ViewModel() {

    private val _requestState = MutableStateFlow<RequestState>(RequestState.Empty)
    val requestState: StateFlow<RequestState> = _requestState

    private val _requestCreateState = MutableStateFlow<RequestCreateState>(RequestCreateState.Empty)
    val requestCreateState: StateFlow<RequestCreateState> = _requestCreateState

    private val _requesDeleteState = MutableStateFlow<RequestDeleteState>(RequestDeleteState.Empty)
    val requesDeleteState: StateFlow<RequestDeleteState> = _requesDeleteState

    private val _requestUpdateState = MutableStateFlow<RequestUpdateState>(RequestUpdateState.Empty)
    val requestUpdateState: StateFlow<RequestUpdateState> = _requestUpdateState

    private val _selectedRequest = MutableStateFlow<Request?>(null)
    val selectedRequest: StateFlow<Request?> = _selectedRequest

    private val _requestList = MutableStateFlow<List<Request>>(emptyList())
    val requestList: StateFlow<List<Request>> = _requestList


    /**
     * Finds a location by its ID and updates the [location] state.
     *
     * @param locationId The ID of the location to find
     */
    fun selectRequestById(requestId: Long) {
        viewModelScope.launch {
            requestRepository.findRequestById(requestId)
                .onSuccess { request ->
                    _selectedRequest.value = request
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error fetching request by ID: ${exception.message}")
                }
        }
    }



    fun findAllRequest() {
        viewModelScope.launch {
            requestRepository.findAllRequests()
                .onSuccess { requests ->
                    if (requests.isEmpty()) {
                        _requestState.value = RequestState.Empty
                    }else {
                        Log.d("RequestViewModel", "Total All Requests: ${requests.size}")
                        _requestState.value = RequestState.Success(requests)
                        _requestList.value = requests
                    }
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Failed to fetch requests location: ${exception.message}")
                }
        }
    }

    fun createRequest(internshipLocation: InternshipLocation) {
        val requestCreate = RequestCreateDto(internshipLocation.id!!)
        viewModelScope.launch {
            requestRepository.saveRequest (requestCreate)
                .onSuccess { request ->
                    // Puedes actualizar el estado si es necesario
                    Log.d("RequestViewModel", "Request created: $request")
                    _requestCreateState.value = RequestCreateState.Success(request)
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error creating request: ${exception.message}")
                    val errorMsg = if (exception.message?.contains("409") == true || exception.message?.contains("Conflict") == true) {
                        "You have already made a request to this internship."
                    } else {
                        "An unexpected error occurred. Please try again."
                    }
                    _requestCreateState.value = RequestCreateState.Error(errorMsg)
                }
        }
    }

    fun createRequestOfInternshipLocationFlag(internshipLocation: InternshipLocationFlagDto) {
        Log.d("RequestViewModel", "Request: $internshipLocation.id ")
        val requestCreate = RequestCreateDto(internshipLocation.id!!)
        val updatedRequest = internshipLocation.copy(requested = !internshipLocation.requested)

        viewModelScope.launch {
            requestRepository.saveRequest (requestCreate)
                .onSuccess { request ->
                    // Puedes actualizar el estado si es necesario
                    Log.d("RequestViewModel", "Request created: $request")
                    _requestCreateState.value = RequestCreateState.Success(request)

                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error creating request: ${exception.message}")
                    val errorMsg = if (exception.message?.contains("409") == true || exception.message?.contains("Conflict") == true) {
                        "You have already made a request to this internship."
                    } else {
                        "An unexpected error occurred. Please try again."
                    }
                    _requestCreateState.value = RequestCreateState.Error(errorMsg)
                }
        }
    }

    fun createRequestOfInternshipLocationFlag(internshipLocation: InternshipLocationRecommendedFlagDto) {
        Log.d("RequestViewModel", "Request: $internshipLocation.id ")
        val requestCreate = RequestCreateDto(internshipLocation.id)
        //val updatedRequest = internshipLocation.copy(requested = !internshipLocation.requested)

        viewModelScope.launch {
            requestRepository.saveRequest (requestCreate)
                .onSuccess { request ->
                    // Puedes actualizar el estado si es necesario
                    Log.d("RequestViewModel", "Request created: $request")
                    _requestCreateState.value = RequestCreateState.Success(request)

                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error creating request: ${exception.message}")
                    val errorMsg = if (exception.message?.contains("409") == true || exception.message?.contains("Conflict") == true) {
                        "You have already made a request to this internship."
                    } else {
                        "An unexpected error occurred. Please try again."
                    }
                    _requestCreateState.value = RequestCreateState.Error(errorMsg)
                }
        }
    }

    fun deleteRequestByInternshipLocationIdAndStudent(internshipLocationId: Long) {

        _requesDeleteState.value = RequestDeleteState.Loading
        viewModelScope.launch {
            requestRepository.deleteRequestByInternshipLocationId (internshipLocationId)
                .onSuccess { request ->
                    // Puedes actualizar el estado si es necesario
                    Log.d("RequestViewModel", "Request deleted: $request")
                    _requesDeleteState.value = RequestDeleteState.Success(request)
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error deleting request: ${exception.message}")
                    val errorMsg = "Error deleting request"
                    _requesDeleteState.value = RequestDeleteState.Error(errorMsg)
                }
        }
    }

    fun deleteRequestById(requestId: Long) {

        _requesDeleteState.value = RequestDeleteState.Loading
        viewModelScope.launch {
            requestRepository.deleteRequest(requestId)
                .onSuccess { request ->
                    // Puedes actualizar el estado si es necesario
                    Log.d("RequestViewModel", "Request deleted: $request")
                    _requesDeleteState.value = RequestDeleteState.Success(request)
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error deleting request: ${exception.message}")
                    val errorMsg = "Error deleting request"
                    _requesDeleteState.value = RequestDeleteState.Error(errorMsg)
                }
        }
    }



    fun updateRequest(request: Request) {
        val updatedRequest = request.copy(state = !request.state)
        val updateRequestDto = RequestUpdateDto(updatedRequest.id, updatedRequest.state, updatedRequest.student.id, updatedRequest.internshipLocation.id!!)
        viewModelScope.launch {
            requestRepository.updateRequest(updateRequestDto)
                .onSuccess { request ->
                    //_selectedRequest.value = request
                    Log.e("RequestViewModel", "Request update correctly: $request")
                    _requestUpdateState.value = RequestUpdateState.Success(request)
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Error fetching request by ID: ${exception.message}")
                    _requestUpdateState.value = RequestUpdateState.Error("Error )-:")
                }
        }
    }


    fun findRequestsbyStudentAndCompany(studentId: Long) {
        viewModelScope.launch {
            requestRepository.findByStudentAndCompany(studentId)
                .onSuccess { requests ->
                    if (requests.isEmpty()) {
                        _requestState.value = RequestState.Empty
                    }else {
                        Log.d("RequestViewModel", "Total Internships: ${requests.size}")
                        _requestState.value = RequestState.Success(requests)
                        _requestList.value = requests
                    }
                }
                .onFailure { exception ->
                    Log.e("RequestViewModel", "Failed to fetch requests location: ${exception.message}")
                }
        }
    }



}