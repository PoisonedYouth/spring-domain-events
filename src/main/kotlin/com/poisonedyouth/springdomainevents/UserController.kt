package com.poisonedyouth.springdomainevents

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
internal class UserController(
    private val userService: UserService
) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/api/v1/user")
    fun createNewUser(
        @RequestBody user: UserDto
    ): ResponseEntity<Any> {
        logger.info("Start creating new user.")
        return userService.handleNewUser(user).let {
            when (it) {
                is ApiResult.Success -> ResponseEntity.status(HttpStatus.CREATED).body(it)
                is ApiResult.Failure -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(it.message)
            }
        }
    }

    @PutMapping("/api/v1/user")
    fun updateUser(
        @RequestBody user: UserDto
    ): ResponseEntity<Any> {
        logger.info("Start updating user.")
        return userService.handleUpdateUser(user).let {
            when (it) {
                is ApiResult.Success -> ResponseEntity.ok().body(it)
                is ApiResult.Failure -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(it.message)
            }
        }
    }

}

data class UserDto(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val street: String,
    val streetNumber: Int,
    val zipCode: Int,
    val city: String
)