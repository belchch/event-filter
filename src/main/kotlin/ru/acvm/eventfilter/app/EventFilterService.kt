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
                .filter { it.data.timeKey >= from }

        val notIssued = notIssuedFilter.apply(eventRange)
                .filter { it.data.timeKey <= (to ?: Long.MAX_VALUE) }

        output.printWriter().use { printWriter ->
            notIssued.forEach { printWriter.println(it.content) }
        }
    }
}