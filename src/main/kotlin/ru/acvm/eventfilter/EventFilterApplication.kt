package ru.acvm.eventfilter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import ru.acvm.eventfilter.app.EventFilterService
import ru.acvm.eventfilter.app.EventParser
import ru.acvm.eventfilter.app.ResourceReader
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.Long.parseLong

@SpringBootApplication
class EventFilterApplication(private val eventFilterService: EventFilterService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (args.isEmpty()){
            throw IllegalArgumentException("Arguments: inputFile, outputFile, fromTimeKey, [toTimeKey]")
        }

        val input = args[0]!!
        val output = args[1]!!
        val from = parseLong(args[2])
        val to = if (args.size > 3) parseLong(args[3]) else null

        eventFilterService.filter(File(input), File(output), from, to)
    }

}

fun main(args: Array<String>) {
    runApplication<EventFilterApplication>(*args)
}
