package de.holhar.kotlin.coursecatalog.service

import de.holhar.kotlin.coursecatalog.dto.InstructorDto
import de.holhar.kotlin.coursecatalog.entity.Instructor
import de.holhar.kotlin.coursecatalog.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    fun createInstructor(instructorDto: InstructorDto): InstructorDto {
        val instructor = instructorDto.let {
            Instructor(it.id, it.name)
        }
        val savedInstructor = instructorRepository.save(instructor)

        return savedInstructor.let {
            InstructorDto(it.id, it.name)
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }
}
