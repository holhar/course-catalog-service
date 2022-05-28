package de.holhar.kotlin.coursecatalog.controller

import de.holhar.kotlin.coursecatalog.service.GreetingsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingsService: GreetingsService) {

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String {
        return greetingsService.retrieveGreeting(name)
    }
}
