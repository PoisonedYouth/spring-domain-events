package com.poisonedyouth.springdomainevents

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class UserService(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun handleNewUser(userDto: UserDto): ApiResult {
        require(userDto.id == null) {
            "New user must not have an id set"
        }
        return try {
            ApiResult.Success(userRepository.save(userDto.toUserAggregate()).also {
                eventPublisher.publishCustomEvent(it.currentDomainEvents())
                it.resetDomainEvents()
            }.id)
        } catch (e: RuntimeException) {
            logger.error("Failed to create new user.", e)
            ApiResult.Failure("Failed to create new user ('$e.message)'")
        }
    }

    fun handleUpdateUser(userDto: UserDto): ApiResult {
        return try {
            ApiResult.Success(userRepository.save(userDto.toUserAggregate()).also {
                eventPublisher.publishCustomEvent(it.currentDomainEvents())
                it.resetDomainEvents()
            }.id)
        } catch (e: RuntimeException) {
            logger.error("Failed to update existing user.", e)
            ApiResult.Failure("Failed to update existing user ('$e.message)'")
        }
    }

    fun UserDto.toUserAggregate() = UserAggregate(
        id = this.id ?: UUID.randomUUID(),
        name = UserAggregate.Name(
            firstName = this.firstName,
            lastName = this.lastName,
        ),
        birthDate = this.birthDate,
        address = UserAggregate.Address(
            street = this.street,
            streetNumber = this.streetNumber,
            zipCode = this.zipCode,
            city = this.city
        )
    )
}

sealed interface ApiResult {

    data class Success (val id: UUID) : ApiResult
    data class Failure(val message: String) : ApiResult
}