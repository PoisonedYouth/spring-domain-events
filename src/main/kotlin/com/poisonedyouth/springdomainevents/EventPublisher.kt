package com.poisonedyouth.springdomainevents

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component


@Component
class EventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun publishCustomEvent(domainEvents: List<UserEvent>) {
        domainEvents.forEach { applicationEventPublisher.publishEvent(it) }
    }

}


