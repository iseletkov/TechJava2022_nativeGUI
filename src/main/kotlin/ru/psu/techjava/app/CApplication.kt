package ru.psu.techjava.app

import ru.psu.techjava.view.CViewStudentTable
import tornadofx.*

class CApplication: App(CViewStudentTable::class, Styles::class)
{
    val api: Rest by inject()
    init {
        api.baseURI = "http://192.168.1.101:8080/"
    }
}