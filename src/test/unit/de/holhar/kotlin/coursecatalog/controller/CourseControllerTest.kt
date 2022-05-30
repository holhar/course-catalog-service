package de.holhar.kotlin.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import de.holhar.kotlin.coursecatalog.dto.CourseDto
import de.holhar.kotlin.coursecatalog.entity.Course
import de.holhar.kotlin.coursecatalog.service.CourseService
import de.holhar.kotlin.coursecatalog.util.courseDto
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {

        val courseDto = CourseDto(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")

        every { courseServiceMock.addCourse(any()) } returns courseDto(id = 1)

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDto!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMock.retrieveAllCourses() }.returnsMany(
            listOf(courseDto(id = 1),
                courseDto(2, "Build Reactive Microservices using Spring WebFlux/SpringBoot"))
        )

        val courseDtos = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("courseDts '$courseDtos'")
        Assertions.assertEquals(2, courseDtos!!.size)
    }

    @Test
    fun updateCourse() {

        // updated CourseDto
        val updatedCourseDto = CourseDto(null, "Build RestFul APis using SpringBoot and Kotlin1", "Development")

        every { courseServiceMock.updateCourse(any(), any()) } returns CourseDto(100, "Build RestFul APis using SpringBoot and Kotlin1", "Development")

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(
            "Build RestFul APis using SpringBoot and Kotlin1",
            updatedCourse!!.name
        )
    }
}
