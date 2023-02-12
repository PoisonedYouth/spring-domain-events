package com.poisonedyouth.springdomainevents

import org.springframework.data.domain.AbstractAggregateRoot
import java.time.LocalDate
import java.util.*

data class UserAggregate(
    val id: UUID = UUID.randomUUID(),
    val name: Name,
    val birthDate: LocalDate,
    val address: Address
) : AbstractAggregateRoot<UserAggregate>() {

    fun registerDomainEvent(event: UserEvent): UserAggregate {
        this.registerEvent(event)
        return this
    }

    fun currentDomainEvents(): List<UserEvent> =
        this.domainEvents().map { it as UserEvent }.toList()

    fun resetDomainEvents() = this.clearDomainEvents()

    data class Name(
        val firstName: String,
        val lastName: String
    ) {
        val fullName = "$lastName $firstName"
    }

    data class Address(
        val street: String,
        val streetNumber: Int,
        val zipCode: Int,
        val city: String
    )
}