package ru.psu.techjava.app

import ru.psu.techjava.model.CStudent
import ru.psu.techjava.view.CViewStudentTable
import tornadofx.*
import java.util.*

class CApplication                          : App(CViewStudentTable::class, Styles::class)
{
    private val api                         : Rest by inject()
    init {
        FX.locale                           = Locale("ru")
        api.baseURI                         = "http://192.168.1.101:8080/"
    }
}