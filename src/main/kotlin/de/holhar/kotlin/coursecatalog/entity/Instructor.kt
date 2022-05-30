package de.holhar.kotlin.coursecatalog.entity

import javax.persistence.*

@Entity
@Table(name = "Instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    val name: String
)
