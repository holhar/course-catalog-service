package de.holhar.kotlin.coursecatalog.service

import de.holhar.kotlin.coursecatalog.dto.CourseDto
import de.holhar.kotlin.coursecatalog.entity.Course
import de.holhar.kotlin.coursecatalog.exception.CourseNotFoundException
import de.holhar.kotlin.coursecatalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    companion object: KLogging()

    fun addCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseDto.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        logger.info("Saved course '$courseEntity'")

        return courseEntity.let {
            CourseDto(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDto> {

        val courses = courseName?.let {
            courseRepository.findByName(courseName)
        } ?: courseRepository.findAll()

        return courses
            .map { CourseDto(it.id, it.name, it.category) }
    }

    fun updateCourse(courseDto: CourseDto, courseId: Int): CourseDto {

        val existingCourse = courseRepository.findById(courseId)

        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDto.name
                it.category = courseDto.category
                courseRepository.save(it)
                CourseDto(it.id, it.name, it.category)
            }
        } else {
            throw CourseNotFoundException("No course found for the passed id '$courseId'")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        if (existingCourse.isPresent) {
            courseRepository.deleteById(courseId)
        } else {
            throw CourseNotFoundException("No course found for the passed id '$courseId'")
        }
    }
}
