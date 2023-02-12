package com.poisonedyouth.springdomainevents

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDomainEventsApplication

fun main(args: Array<String>) {
    runApplication<SpringDomainEventsApplication>(*args)
}
