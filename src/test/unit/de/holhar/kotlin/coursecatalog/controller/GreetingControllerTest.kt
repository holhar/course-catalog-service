package de.holhar.kotlin.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import de.holhar.kotlin.coursecatalog.service.GreetingsService
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock : GreetingsService

    @Test
    fun retrieveGreeting() {
        val name = "Holger"

        every { greetingServiceMock.retrieveGreeting(name) } returns "$name, Hello from default profile"

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("$name, Hello from default profile", result.responseBody)
    }
}
