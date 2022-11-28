package ru.psu.techjava.viewmodels

import ru.psu.techjava.model.CStudent
import ru.psu.techjava.services.CServiceStudents
import tornadofx.*

class CViewModelStudent(
    student                                 : CStudent
)                                           : ItemViewModel<CStudent>(student)
{
    private val serviceStudents             : CServiceStudents by inject()
    val id                                  = bind(CStudent::propertyId)
    val name                                = bind(CStudent::propertyName)
    val year                                = bind(CStudent::propertyYear)
    val studyGroup                          = bind(CStudent::propertyStudyGroup)

    fun save()
    {
        this.commit()
        serviceStudents.save(this.item)
    }
}