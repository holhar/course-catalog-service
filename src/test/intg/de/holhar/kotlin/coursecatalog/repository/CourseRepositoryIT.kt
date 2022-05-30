package de.holhar.kotlin.coursecatalog.repository

import de.holhar.kotlin.coursecatalog.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIT {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    internal fun setUp() {
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun findByNameContaining() {
        val courses = courseRepository.findByNameContaining("SpringBoot")
        println("courses '$courses'")
        Assertions.assertEquals(2, courses.size)
    }

    @Test
    fun findByName_nativeQuery() {
        val courses = courseRepository.findByName("SpringBoot")
        println("courses '$courses'")
        Assertions.assertEquals(2, courses.size)
    }
}
