package com.poisonedyouth.springdomainevents

import java.time.Instant
import java.util.*

sealed interface UserEvent {
    val id: UUID
    val timestamp: Instant
    val aggregateId: UUID
}

data class UpdateUserEvent(
    override val aggregateId: UUID
) : UserEvent {
    override val id: UUID = UUID.randomUUID()
    override val timestamp: Instant = Instant.now()

    override fun toString(): String {
        return "UpdateUserEvent(aggregateId=$aggregateId, id=$id, timestamp=$timestamp)"
    }
}

data class NewUserEvent(
    override val aggregateId: UUID
) : UserEvent {
    override val id: UUID = UUID.randomUUID()
    override val timestamp: Instant = Instant.now()

    override fun toString(): String {
        return "NewUserEvent(aggregateId=$aggregateId, id=$id, timestamp=$timestamp)"
    }
}