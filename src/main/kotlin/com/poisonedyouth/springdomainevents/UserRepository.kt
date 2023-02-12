package com.poisonedyouth.springdomainevents

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
internal class UserRepository {

    fun save(userAggregate: UserAggregate): UserAggregate {
        val existingUser = findById(userAggregate.id)
        return if (existingUser != null) {
            UserTable.update {
                it[firstName] = userAggregate.name.firstName
                it[lastName] = userAggregate.name.lastName
                it[birthDate] = userAggregate.birthDate
                it[street] = userAggregate.address.street
                it[streetNumber] = userAggregate.address.streetNumber
                it[zipCode] = userAggregate.address.zipCode
                it[city] = userAggregate.address.city
            }
            userAggregate.registerDomainEvent(UpdateUserEvent(userAggregate.id))
        } else {
            val id = UserTable.insertAndGetId {
                it[firstName] = userAggregate.name.firstName
                it[lastName] = userAggregate.name.lastName
                it[birthDate] = userAggregate.birthDate
                it[street] = userAggregate.address.street
                it[streetNumber] = userAggregate.address.streetNumber
                it[zipCode] = userAggregate.address.zipCode
                it[city] = userAggregate.address.city
            }.value
            userAggregate.copy(
                id = id
            ).registerDomainEvent(NewUserEvent(id))
        }
    }


    fun findById(aggregateId: UUID): UserAggregate? {
        return UserTable.select { UserTable.id eq aggregateId }.firstOrNull()?.let {
            it.toUserAggregate()
        }
    }

    private fun ResultRow.toUserAggregate() = UserAggregate(
        id = this[UserTable.id].value,
        name = UserAggregate.Name(
            firstName = this[UserTable.firstName],
            lastName = this[UserTable.lastName],
        ),
        birthDate = this[UserTable.birthDate],
        address = UserAggregate.Address(
            street = this[UserTable.street],
            streetNumber = this[UserTable.streetNumber],
            zipCode = this[UserTable.zipCode],
            city = this[UserTable.city]
        )
    )


    object UserTable : UUIDTable("user", "id") {
        val firstName = varchar("first_name", 255)
        val lastName = varchar("last_name", 255)
        val birthDate = date("birth_date")
        val street = varchar("street", 255)
        val streetNumber = integer("street_number")
        val zipCode = integer("zip_code")
        val city = varchar("city", 255)
    }
}