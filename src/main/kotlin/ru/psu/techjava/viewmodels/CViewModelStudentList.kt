package ru.psu.techjava.viewmodels

import ru.psu.techjava.services.CServiceStudents
import tornadofx.*

class CViewModelStudentList                 : Controller()
{
    private val serviceStudents             : CServiceStudents by inject()

    val students                            = serviceStudents.getAll()
}