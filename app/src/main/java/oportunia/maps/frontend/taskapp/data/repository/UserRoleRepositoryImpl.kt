package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.UserMapper
import oportunia.maps.frontend.taskapp.data.mapper.UserRoleMapper
import oportunia.maps.frontend.taskapp.data.remote.UserRoleRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleCreateDto
import oportunia.maps.frontend.taskapp.domain.model.UserRole
import oportunia.maps.frontend.taskapp.domain.repository.UserRoleRepository
import java.net.UnknownHostException
import javax.inject.Inject

class UserRoleRepositoryImpl @Inject constructor(
    private val dataSource: UserRoleRemoteDataSource,
    private val userRoleMapper: UserRoleMapper,
    private val userMapper: UserMapper
) : UserRoleRepository {


    /**
     * Retrieves all user roles from the data source.
     *
     * @return [Result] containing a list of user roles if successful, or an error if the operation failed
     */


    override suspend fun findAllUserRoles(): Result<List<UserRole>> {
        return try {
            dataSource.getAll().map { dtos ->
                dtos.map { userRoleMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching location companies: ${e.message}"))
        }
    }



    override suspend fun saveUserRole(userRole: UserRoleCreateDto): Result<UserRole> {
        return dataSource.create(userRole).map {
            userRoleMapper.mapToDomain(it)
        }
    }



    /*
    override suspend fun findAllUserRoles(): Result<List<UserRole>> = runCatching {
        dataSource.getUserRoles().first().map { userRoleDto ->
            userRoleMapper.mapToDomain(userRoleDto)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user roles")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user roles")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findUserRoleById(id: Long): Result<UserRole> = runCatching {
        val userRoleDto =
            dataSource.getUserRoleById(id) ?: throw DomainError.TaskError("User role not found")
        userRoleMapper.mapToDomain(userRoleDto)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user role")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user role")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findUsersByRole(role: UserRole): Result<List<User>> = runCatching {
        // Fetch users by role from the data source
        dataSource.getUsersByRole(role.role.name.name).first().map { userDto ->
            userMapper.mapToDomain(userDto.user)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch users for role")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping users for role")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun postUserRole(email: String, password: String): Result<UserRole?> = runCatching {
        val userRoleDto = dataSource.getUserRoleByEmail(email).firstOrNull()
        userRoleDto?.let {
            userRoleMapper.mapToDomain(it)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user role by email")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user role")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun getUserRoleByEmail(email: String): Result<UserRole?> = runCatching {
        val userRoleDto = dataSource.getUserRoleByEmail(email).firstOrNull()
        userRoleDto?.let {
            userRoleMapper.mapToDomain(it)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch user role by email")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user role")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun saveUserRole(userRole: UserRole): Result<Unit> = runCatching {
        dataSource.insertUserRole(userRoleMapper.mapToDto(userRole))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to save user role")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user role")
            else -> throw DomainError.TaskError("Failed to save user role: ${throwable.message}")
        }
    }



    override suspend fun deleteUserRole(id: Long): Result<Unit> = runCatching {
        val userRole = dataSource.getUserRoleById(id)
            ?: throw DomainError.TaskError("User role not found")
        dataSource.deleteUserRole(userRole)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to delete user role")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun updateUserRole(userRole: UserRole): Result<Unit> = runCatching {
        dataSource.updateUserRole(userRoleMapper.mapToDto(userRole))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to update user role")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping user role")
            else -> throw DomainError.TaskError("Failed to update user role: ${throwable.message}")
        }
    }

     */
}