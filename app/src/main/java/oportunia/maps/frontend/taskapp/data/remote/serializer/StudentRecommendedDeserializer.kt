package oportunia.maps.frontend.taskapp.data.remote.serializer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import java.lang.reflect.Type

/**
 * Custom JSON deserializer for [QualificationDto] objects.
 *
 * Handles nested LatLng and Qualification deserialization.
 */
class StudentRecommendedDeserializer : JsonDeserializer<StudentRecommendedDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): StudentRecommendedDto {
        val jsonObject = json.asJsonObject
        Log.d("StudentDeserializer", "JSON recibido: $json")



        val studentId = jsonObject.get("idStudent").asLong
        val studentName = jsonObject.get("nameStudent").asString
        val identification = jsonObject.get("identification").asString
        val personalInfo = jsonObject.get("personalInfo").asString
        val experience = jsonObject.get("experience").asString
        val rating = jsonObject.get("ratingStudent").asDouble
        val homeLatitude = jsonObject.get("homeLatitude").asDouble
        val homeLongitude = jsonObject.get("homeLongitude").asDouble

        val imageProfile = try {
            jsonObject.get("imageProfile").asString ?: "empty"
        } catch (e: Exception) {
            Log.e("StudentDeserializer", "Error al obtener 'imageProfile': ${e.message}")
            "empty"
        }


        // Deserialize user information
        val userObject = jsonObject.getAsJsonObject("user")
        val userId = userObject.get("id").asLong
        val firstName = userObject.get("firstName").asString
        val lastName = userObject.get("lastName").asString
        val emailUser = userObject.get("email").asString
        val enable = userObject.get("enabled").asBoolean
        val tokenExpired = try {
            userObject.get("tokenExpired")?.asBoolean ?: false
        } catch (e: Exception) {
            Log.e("StudentDeserializer", "Error al obtener 'userObject': ${e.message}")
            false
        }
        val createDate = userObject.get("createDate").asString
        val roleListJson = userObject.getAsJsonArray("roleList")
        val roleList: List<RoleDto> = roleListJson.map { element ->
            val roleObject = element.asJsonObject
            val idrole = roleObject.get("id").asLong
            val name = roleObject.get("name").asString

            RoleDto(id = idrole, name = name)
        }

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, emailUser, firstName, lastName, enable, tokenExpired, createDate, roleList)

        val score = jsonObject.get("score").asInt
        val reason = jsonObject.get("reason").asString

        // Return the StudentDto object
        val student = StudentRecommendedDto(
            studentId, studentName, identification, personalInfo,
            experience, rating, userDto, imageProfile, homeLatitude, homeLongitude, score, reason
        )
        Log.d("StudentDeserializer", "JSON en Student DTO: $student")
        return student
    }
}