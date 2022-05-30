package de.holhar.kotlin.coursecatalog.dto

import javax.validation.constraints.NotBlank

data class InstructorDto(
    val id: Int?,
    @get:NotBlank(message = "instructor.name must not be blank")
    val name: String
)
