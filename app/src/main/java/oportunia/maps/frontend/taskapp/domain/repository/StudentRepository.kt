package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.StudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.Student

interface StudentRepository {

    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Long): Result<Student>
    //suspend fun saveStudent(student: Student): Result<Student>
    suspend fun deleteStudent(studentId: Long): Result<Unit>
    suspend fun updateStudent(student: Student): Result<Unit>
    suspend fun findLoggedStudent(): Result<Student>
    suspend fun findRecommendedStudents(): Result<List<StudentRecommendedDto>>
    suspend fun findStudentsRequestingMyCompany(): Result<List<Student>>
    suspend fun saveStudent(student: StudentCreateDto): Result<Student>
}

