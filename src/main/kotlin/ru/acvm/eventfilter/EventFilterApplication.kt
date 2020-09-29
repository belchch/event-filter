package ru.acvm.eventfilter

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.acvm.eventfilter.app.DateParser
import ru.acvm.eventfilter.app.EventFilterService
import java.io.File
import java.text.ParseException
import java.util.*

@SpringBootApplication
class EventFilterApplication(
        private val eventFilterService: EventFilterService,
        private val dateParser: DateParser
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (args.size < 4){
            println("Arguments: inputFile, outputFile, fromDate, toDate")
        }

        val input = args[0]!!
        val output = args[1]!!
        val from: Date? = parseDate(args[2]!!)
        val to: Date? = parseDate(args[3]!!)

        if (from != null && to != null){
            eventFilterService.filter(File(input), File(output), from, to)
        }
    }

    private fun parseDate(text: String): Date? {
        return try {
            dateParser.parse(text)
        } catch (e: ParseException) {
            println("Date format: dd.MM.yyyy hh:mm")
            null
        }
    }
}

fun main(args: Array<String>) {
    runApplication<EventFilterApplication>(*args)
}
