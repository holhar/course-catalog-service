package de.holhar.kotlin.coursecatalog.repository

import de.holhar.kotlin.coursecatalog.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String) : List<Course>

    @Query(value = "select * from courses where name like %?1%", nativeQuery = true)
    fun findByName(courseName: String) : List<Course>
}
