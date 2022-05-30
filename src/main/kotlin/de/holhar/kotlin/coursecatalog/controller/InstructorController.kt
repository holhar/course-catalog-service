package de.holhar.kotlin.coursecatalog.controller

import de.holhar.kotlin.coursecatalog.dto.InstructorDto
import de.holhar.kotlin.coursecatalog.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(val instructorService: InstructorService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody instructorDto: InstructorDto) = instructorService.createInstructor(instructorDto)
}
