package de.holhar.kotlin.coursecatalog.controller

import de.holhar.kotlin.coursecatalog.dto.CourseDto
import de.holhar.kotlin.coursecatalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDto: CourseDto): CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName: String?): List<CourseDto> = courseService.retrieveAllCourses(courseName)

    // courseId
    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDto: CourseDto, @PathVariable("course_id") courseId: Int): CourseDto = courseService.updateCourse(courseDto, courseId)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int) = courseService.deleteCourse(courseId)
}
