package ru.acvm.eventfilter.app

import org.springframework.stereotype.Component
import java.io.File

@Component
class EventFilterService(
        private val notIssuedFilter: NotIssuedFilter,
        private val parser: EventParser
) {

    fun filter(input: File, output: File, from: Long, to: Long?) {
        val eventRange = input.readLines()
                .map { parser.parse(it) }
                .filter { it.data.timeKey in from..(to ?: Long.MAX_VALUE) }

        val notIssued = notIssuedFilter.apply(eventRange)

        output.printWriter().use { printWriter ->
            notIssued.forEach { printWriter.println(it.content) }
        }
    }
}