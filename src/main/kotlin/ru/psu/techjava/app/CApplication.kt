package ru.psu.techjava.app

import ru.psu.techjava.model.CStudent
import ru.psu.techjava.view.CViewStudentTable
import tornadofx.*
import java.util.*

class CApplication                          : App(CViewStudentTable::class, Styles::class)
{
    private val api                         : Rest by inject()
    init {
        //Язык интерфейса приложения.
        FX.locale                           = Locale("ru")
        //Базовая часть адреса для подключения к API сервера.
        api.baseURI                         = "http://192.168.1.101:8080/"
    }
}