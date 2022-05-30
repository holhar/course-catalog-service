package de.holhar.kotlin.coursecatalog.dto

import javax.validation.constraints.NotBlank

data class CourseDto(
    val id: Int?,
    // POI: https://kotlinlang.org/docs/annotations.html#annotation-use-site-targets
    @get:NotBlank(message = "courseDto.name must not be blank")
    val name: String,
    @get:NotBlank(message = "courseDto.category must not be blank")
    val category: String
)
