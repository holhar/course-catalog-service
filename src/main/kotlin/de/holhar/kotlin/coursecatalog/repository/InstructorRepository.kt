package de.holhar.kotlin.coursecatalog.repository

import de.holhar.kotlin.coursecatalog.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {
}
