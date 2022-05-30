package de.holhar.kotlin.coursecatalog.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingsService {

    @Value("\${app.message}")
    lateinit var message: String

    fun retrieveGreeting(name: String) = "$name, $message"
}
