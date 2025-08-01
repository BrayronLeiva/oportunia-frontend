package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
import javax.inject.Inject

/**
 * Mapper class for converting between Internship domain entities and InternshipDto data objects
 */
class InternshipMapper @Inject constructor(){

    /**
     * Maps an InternshipDto to a domain Internship entity
     * @param dto The data layer internship object to convert
     * @return Domain Internship object
     */
    fun mapToDomain(dto: InternshipDto): Internship = Internship(
        id = dto.id,
        details = dto.details,
    )

    /**
     * Maps a domain Internship to an InternshipDto
     * @param domain The domain layer internship object to convert
     * @return InternshipDto object for data layer
     */
    fun mapToDto(domain: Internship): InternshipDto =
        InternshipDto(
            id = domain.id,
            details = domain.details,
        )
}
