package ru.acvm.eventfilter.app

import org.springframework.stereotype.Component

@Component
class NotIssuedFilter {
    fun apply(events: List<Event>): List<Event> {
        return events
                .groupingBy { it.data.cardParams.cardId }
                .fold({_,_ -> CustomerEvents()}, accumulate)
                .values
                .filter { it.issued == null && it.delivered != null }
                .map { it.delivered!! }
    }
}

private val accumulate: (key: String, accumulator: CustomerEvents, event: Event) -> CustomerEvents = { _, accumulator, event ->
    when (event.data.cardParams.cardStatus) {
        "Delivered" -> accumulator.delivered = event
        "Issued" -> accumulator.issued = event
        else -> throw IllegalArgumentException("Invalid card status ${event.data.cardParams.cardStatus}")
    }
    accumulator
}

data class CustomerEvents(
        var issued: Event? = null,
        var delivered: Event? = null
)