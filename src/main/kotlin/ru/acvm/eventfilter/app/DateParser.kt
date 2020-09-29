package ru.acvm.eventfilter.app

import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class DateParser {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")

    fun parse(text: String): Date {
        return dateFormat.parse(text)
    }
}