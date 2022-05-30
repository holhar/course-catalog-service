package de.holhar.kotlin.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import de.holhar.kotlin.coursecatalog.dto.CourseDto
import de.holhar.kotlin.coursecatalog.service.CourseService
import de.holhar.kotlin.coursecatalog.util.courseDto
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.lang.RuntimeException

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {

        val courseDto = CourseDto(null, "Build Restful APIs using Kotlin and SpringBoot", "Development", 1)

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
    fun addCourse_validation() {

        val courseDto = CourseDto(null, "Build Restful APIs using Kotlin and SpringBoot", "Development", 1)

        val errorMessage = "Unexpected error occurred"
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }

    @Test
    fun addCourse_runtimeException() {

        val courseDto = CourseDto(null, "", "", 1)

        every { courseServiceMock.addCourse(any()) } returns courseDto(id = 1)

        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDto.category must not be blank, courseDto.name must not be blank", response)
    }

    @Test
    fun retrieveAllCourses() {

        every { courseServiceMock.retrieveAllCourses(any()) }.returnsMany(
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

    @Test
    fun deleteCourse() {

        every { courseServiceMock.deleteCourse(any()) } just runs

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }
}
