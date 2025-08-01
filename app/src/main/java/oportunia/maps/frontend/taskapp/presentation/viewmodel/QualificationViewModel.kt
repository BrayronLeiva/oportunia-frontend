package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.Qualification
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import javax.inject.Inject


sealed class QualificationState {
    /** Indicates an ongoing qualification operation */
    data object Loading : QualificationState()

    /** Contains the successfully retrieved qualification */
    data class Success(val qualification: Qualification) : QualificationState()

    /** Indicates no qualification is available */
    data object Empty : QualificationState()

    /** Contains an error message if the qualification operation fails */
    data class Error(val message: String) : QualificationState()
}

/**
 * ViewModel responsible for managing task-related UI state and business logic.
 *
 * @property repository Repository interface for task operations
 */
@HiltViewModel
class QualificationViewModel @Inject constructor(
    private val repository: QualificationRepository
) : ViewModel() {

    private val _qualificationList = MutableStateFlow<List<Qualification>>(emptyList())
    val qualificationList: StateFlow<List<Qualification>> = _qualificationList

    /**
     * Retrieves all available qualifications and updates the [qualificationList] state.
     * Should be called when the ViewModel is initialized or when a refresh is needed.
     */
    fun findAllQualifications() {
        viewModelScope.launch {
            repository.findAllQualifications()
                .onSuccess { qualifications ->
                    Log.d("QualificationViewModel", "Total Qualifications: ${qualifications.size}")
                    _qualificationList.value = qualifications
                }
                .onFailure { exception ->
                    Log.e("QualificationViewModel", "Failed to fetch qualifications: ${exception.message}")
                }
        }
    }
}