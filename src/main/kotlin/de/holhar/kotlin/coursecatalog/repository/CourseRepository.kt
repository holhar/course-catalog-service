package de.holhar.kotlin.coursecatalog.repository

import de.holhar.kotlin.coursecatalog.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String) : List<Course>
}
