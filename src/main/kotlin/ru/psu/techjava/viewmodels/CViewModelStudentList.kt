package ru.psu.techjava.viewmodels

import javafx.beans.property.SimpleBooleanProperty
import ru.psu.techjava.model.CStudent
import ru.psu.techjava.services.CServiceStudents
import tornadofx.*

class CViewModelStudentList                 : Controller()
{
    private val serviceStudents             : CServiceStudents by inject()

    val students                            = serviceStudents.getAll()

    val deleteEnabled                       = SimpleBooleanProperty(false)
    fun add()
    {
        serviceStudents.add(CStudent()) //Передача данных на сервер
    }
    fun delete(
        selectedItem                        : CStudent?
    )
    {
        selectedItem ?: return
        serviceStudents.delete(selectedItem) //Передача данных на сервер
    }
    fun setTableSelection(
        selectedItem                        : CStudent?
    )
    {
        deleteEnabled.set(selectedItem != null)
    }
    fun saveAll()
    {
        serviceStudents.saveAll() //Передача данных на сервер
    }

}