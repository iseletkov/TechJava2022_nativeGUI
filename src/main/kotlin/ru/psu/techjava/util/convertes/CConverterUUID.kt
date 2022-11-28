package ru.psu.techjava.util.convertes

import javafx.util.StringConverter
import java.util.*

object CConverterUUID                       : StringConverter<UUID>() {
    override fun toString(
        id                                  : UUID?
    )                                       = id?.toString() ?: ""
    override fun fromString(
        string                              : String?
    )                                       = if (string == null) null else UUID.fromString(string)
}