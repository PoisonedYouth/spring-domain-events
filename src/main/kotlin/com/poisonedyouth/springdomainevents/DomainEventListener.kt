package com.poisonedyouth.springdomainevents

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
internal class DomainEventListener {

    @EventListener
    fun onApplicationEvent(event: UserEvent) {
        println("New DomainEvent arrived '$event'")
    }
}