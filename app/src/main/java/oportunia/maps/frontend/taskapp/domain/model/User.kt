package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents a user.
 * @property id The unique identifier of the user
 * @property email The email of the user
 * @property password The password of the user
 */

data class User(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val tokenExpired: Boolean,
    val createDate: String,
    val roles: List<Role>,
    val password: String = ""
)
