package ru.acvm.eventfilter.app

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class EventParser(private val objectMapper: ObjectMapper) {
    fun parse(content: String): Event {
        val data = objectMapper.readValue<EventData>(content, EventData::class.java)
        return Event(content, data)
    }
}

data class Event (
        val content: String,
        val data: EventData
)

data class EventData (
        @JsonProperty("timeKey")
        val timeKey: Long,
        @JsonProperty("cardParams")
        val cardParams: CardParams,
        @JsonProperty("customerId")
        val customerId: String
)

data class CardParams (
        @JsonProperty("cardStatus")
        val cardStatus: String
)