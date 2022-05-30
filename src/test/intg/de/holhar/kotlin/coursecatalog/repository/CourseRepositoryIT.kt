package de.holhar.kotlin.coursecatalog.repository

import de.holhar.kotlin.coursecatalog.util.courseEntityList
import de.holhar.kotlin.coursecatalog.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIT {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    internal fun setUp() {
        instructorRepository.deleteAll()
        courseRepository.deleteAll()

        val instructor = instructorEntity()
        instructorRepository.save(instructor)

        val courses = courseEntityList(instructor)
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

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_parameterized(courseName: String, expectedSize: Int) {
        val courses = courseRepository.findByName(courseName)
        println("courses '$courses'")
        Assertions.assertEquals(expectedSize, courses.size)
    }

    companion object {

        @JvmStatic
        fun courseAndSize() : Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("SpringBoot", 2),
                Arguments.arguments("Wiremock", 1)
            )
        }
    }
}
