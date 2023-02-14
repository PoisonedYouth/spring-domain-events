package com.poisonedyouth.springdomainevents

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
internal class DomainEventListener {

    @EventListener
    @Async
    fun onApplicationEvent(event: UserEvent) {
        println("New DomainEvent arrived '$event'")
    }
}