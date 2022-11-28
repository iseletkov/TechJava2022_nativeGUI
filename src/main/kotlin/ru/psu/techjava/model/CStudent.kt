package ru.psu.techjava.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.Year
import java.util.*
import javax.json.JsonObject

class CStudent(
    id                                      : UUID?
                                            = null,
    name                                    : String
                                            = "",
    studyGroup                              : String
                                            = "",
    year                                    : Year
                                            = Year.parse("2000")
)                                           : JsonModel
{
    val propertyId                          = SimpleObjectProperty(id)
    var id by propertyId

    val propertyName                        = SimpleStringProperty(name)
    var name by propertyName

    val propertyStudyGroup                  = SimpleStringProperty(studyGroup)
    var studyGroup by propertyStudyGroup

    val propertyYear                        = SimpleObjectProperty(year)
    var year by propertyYear

    override fun updateModel(
        json                                : JsonObject
    )
    {
        with(json) {
            id                              = UUID.fromString(string("id"))
            name                            = string("name")
            studyGroup                      = string("studyGroup")
            year                            = Year.parse(string("year"))
        }
    }

    override fun toJSON(
        json                                : JsonBuilder
    )
    {
        with(json) {
            add("id", id)
            add("name", name)
            add("studyGroup", studyGroup)
            add("year", year.toString())
        }
    }
}