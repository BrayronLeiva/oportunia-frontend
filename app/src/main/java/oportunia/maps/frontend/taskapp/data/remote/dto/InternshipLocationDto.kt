package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Internship.
 * @property id The unique identifier of the certification.
 * @property locationCompany The location of the Internship.
 * @property internship The Internship at the specific location.
 */

data class InternshipLocationDto (
    val id: Long? = null,
    val locationCompany: LocationCompanyDto,
    val internship: InternshipDto
)

data class InternshipLocationRequestDto(
    val internshipId: Long,
    val locationCompanyId: Long
)

data class InternshipLocationRecommendedDto (
    val id: Long,
    val locationCompany: LocationCompanyDto,
    val internship: InternshipDto,
    val score: Double,
    val reason: String
)

data class InternshipLocationFlagDto (
    val id: Long? = null,
    val locationCompany: LocationCompanyDto,
    val internship: InternshipDto,
    val requested: Boolean
)

data class InternshipLocationRecommendedFlagDto (
    val id: Long,
    val locationCompany: LocationCompanyDto,
    val internship: InternshipDto,
    val score: Double,
    val reason: String,
    val requested: Boolean
)