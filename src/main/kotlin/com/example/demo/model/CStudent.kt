package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.*

class CStudent(
    id: UUID,
    name: String
)
{
    val idProperty = SimpleObjectProperty<UUID>(id)
    var id by idProperty

    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
}