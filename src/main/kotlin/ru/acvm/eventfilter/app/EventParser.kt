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
        @JsonProperty("cardParams")
        val cardParams: CardParams
)

data class CardParams (
        @JsonProperty("cardStatus")
        val cardStatus: String,
        @JsonProperty("cardId")
        val cardId: String,
        @JsonProperty("delivToOffice")
        val deliveredToOffice: String?,
        @JsonProperty("delivToClient")
        val deliveredToClient: String?

) {
    val delivered: String?
        get() = deliveredToOffice ?: deliveredToClient
}