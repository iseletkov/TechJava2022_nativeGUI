package ru.psu.techjava.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.Year
import java.util.*
import javax.json.JsonObject
/********************************************************************************************************
 * Студент.                                                                                             *
 * @author Селетков И.П. 2022 1128.                                                                     *
 *******************************************************************************************************/
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

    /****************************************************************************************************
     * Обновление полей текущего класса по данным из JSON.                                              *
     ***************************************************************************************************/
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
    /****************************************************************************************************
     * Создание JSON по данным из полей текущего класса.                                                *
     ***************************************************************************************************/
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
    /****************************************************************************************************
     * Проверка на соответствие двух объектов друг другу по идентификтаорам.                            *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает туже сущность.                                               *
     ***************************************************************************************************/
    override fun equals(
        other                               : Any?
    )                                       = (other is CStudent)
            && id == other.id
    /****************************************************************************************************
     * Проверка наличия изменений в объекте по сравнению с другим объектом.                             *
     * @param other - другой обект для проверки.                                                        *
     * @return true, если объект описывает ту же сущность, но имеет отличия в полях.                    *
     ***************************************************************************************************/
    fun hasChanges(
        other                               : CStudent
    )                                       =
        equals(other) && //Совсем другой объект не считаем изменённым текущим.
        ( //Изменения в любом другом поле считаем изменениями в объекте.
            name != other.name ||
            studyGroup != other.studyGroup ||
            year != other.year
        )
}