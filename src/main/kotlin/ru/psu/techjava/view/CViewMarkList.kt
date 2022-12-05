package ru.psu.techjava.view

import javafx.scene.layout.BorderPane
import ru.psu.techjava.model.CStudent
import tornadofx.*
/********************************************************************************************************
 * Страница со списком оценок.                                                                          *
 * @author Селетков И.П. 2022 1205.                                                                     *
 *******************************************************************************************************/
class CViewMarkList: View("Marks")
{
    //Студент, по которому показывать оценки.
    val student                             : CStudent? by param()

    //Тестовое содержимое, надо расписать самостоятельно под вашу предметную область.
    override val root                       = BorderPane()
    init {
        root.top {
            button(student?.name?:"Не указан") {
            }
        }
    }

}