package ru.acvm.eventfilter.app

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class NotIssuedFilterTest {
    private val notIssuedFilter = NotIssuedFilter()

    @Test
    fun apply() {
        val a = issued("1")
        val b = delivered("2")
        val c = delivered("1")

        val result = notIssuedFilter.apply(mutableListOf(a, b, c))

        assertEquals(listOf(b), result)
    }

    private fun issued(id: String) = event(id, "Issued")
    private fun delivered(id: String) = event(id, "Delivered")

    private fun event(id: String, cardStatus: String) = Event(
            data = EventData(
                    timeKey = 1,
                    cardParams = CardParams(
                            cardStatus = cardStatus,
                            cardId = id
                    )),
            content = ""
    )
}