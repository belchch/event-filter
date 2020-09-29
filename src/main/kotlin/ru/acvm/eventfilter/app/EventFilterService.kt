package ru.acvm.eventfilter.app

import org.springframework.stereotype.Component
import java.io.File
import java.util.*

@Component
class EventFilterService(
        private val notIssuedFilter: NotIssuedFilter,
        private val parser: EventParser,
        private val dateParser: DateParser
) {

    fun filter(input: File, output: File, from: Date, to: Date) {
        val eventRange = input.readLines()
                .map { parser.parse(it) }
                .filter { afterOrEqual(it.data.cardParams.delivered!!, from) }

        val notIssued = notIssuedFilter.apply(eventRange)
                .filter { beforeOrEqual(it.data.cardParams.delivered!!, to) }

        output.printWriter().use { printWriter ->
            notIssued.forEach { printWriter.println(it.content) }
        }
    }

    private fun afterOrEqual(dateText: String, example: Date): Boolean {
        val date = dateParser.parse(dateText)
        return date.after(example) || date == example
    }

    private fun beforeOrEqual(dateText: String, example: Date): Boolean {
        val date = dateParser.parse(dateText)
        return date.before(example) || date == example
    }
}