package ru.acvm.eventfilter

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.acvm.eventfilter.app.EventFilterService
import java.io.File
import java.lang.Long.parseLong

@SpringBootApplication
class EventFilterApplication(private val eventFilterService: EventFilterService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (args.size < 3){
            println("Arguments: inputFile, outputFile, fromTimeKey, [toTimeKey]")
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
