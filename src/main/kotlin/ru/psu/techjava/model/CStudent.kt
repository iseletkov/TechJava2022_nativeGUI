package ru.psu.techjava.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.Year
import java.util.*

class CStudent(
    id: UUID,
    name: String,
    group: String,
    year: Year
)                                           : JsonModel
{
    val propertyId = SimpleObjectProperty(id)
    var id by propertyId

    val propertyName = SimpleStringProperty(name)
    var name by propertyName

    val propertyGroup = SimpleStringProperty(group)
    var group by propertyGroup

    val propertyYear = SimpleObjectProperty(year)
    var year by propertyYear
}