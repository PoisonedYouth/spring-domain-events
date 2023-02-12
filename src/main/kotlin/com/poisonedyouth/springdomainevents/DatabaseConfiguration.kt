package com.poisonedyouth.springdomainevents

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfiguration {

    @Bean
    fun transactionManager(dataSource: HikariDataSource): SpringTransactionManager {
        Database.connect(dataSource)
        transaction{
            SchemaUtils.createMissingTablesAndColumns(UserRepository.UserTable)
        }
        return SpringTransactionManager(
            dataSource
        )
    }
}