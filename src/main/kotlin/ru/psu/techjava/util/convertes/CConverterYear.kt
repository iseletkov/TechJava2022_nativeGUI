package ru.psu.techjava.util.convertes

import javafx.util.StringConverter
import java.time.Year

object  CConverterYear                      : StringConverter<Year>() {
    override fun toString(
        id                                  : Year?
    )                                       = id?.toString() ?: ""
    override fun fromString(
        string                              : String?
    )                                       : Year?
    {
        if (string == null) return null
        if (string.length!=4) return null
        return try {
            Year.parse(string)
        }
        catch (e                            : Exception)
        {
            null
        }
    }
}