package ru.psu.techjava.viewmodels

import ru.psu.techjava.model.CStudent
import ru.psu.techjava.services.CServiceStudents
import tornadofx.*

/********************************************************************************************************
 * Модель представления для одного студента.                                                            *
 * @author Селетков И.П. 2022 1128.                                                                     *
 *******************************************************************************************************/
class CViewModelStudent(
    student                                 : CStudent
)                                           : ItemViewModel<CStudent>(student)
{
    private val serviceStudents             : CServiceStudents by inject()

    val id                                  = bind(CStudent::propertyId)
    val name                                = bind(CStudent::propertyName)
    val year                                = bind(CStudent::propertyYear)
    val studyGroup                          = bind(CStudent::propertyStudyGroup)

    /****************************************************************************************************
     * Сохранение данных из полей формы в объект в оперативной памяти.                                  *
     ***************************************************************************************************/
    fun save()
    {
        this.commit() //Сохранение данных из полей на форме в сущность

        //Передача данных на сервер (изменили на кэш только)
        serviceStudents.save(this.item)
    }


}