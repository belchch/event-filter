package ru.acvm.eventfilter.app

import org.springframework.stereotype.Component
import java.io.File

@Component
class EventFilterService(
        private val notIssuedFilter: NotIssuedFilter,
        private val parser: EventParser
) {

    fun filter(input: File, output: File, from: Long, to: Long?) {
        var events = input.readLines()
                .map { parser.parse(it) }

        events = notIssuedFilter.apply(events)
                .filter { it.data.timeKey <= (to ?: Long.MAX_VALUE) }
                .filter { it.data.timeKey >= from }

        output.printWriter().use { printWriter ->
            events.forEach { printWriter.println(it.content) }
        }
    }
}