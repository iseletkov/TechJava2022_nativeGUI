package ru.psu.techjava.view

import ru.psu.techjava.model.CStudent
import ru.psu.techjava.services.CServiceStudents
import ru.psu.techjava.util.convertes.CConverterYear
import tornadofx.*
import java.time.Year
import java.util.*

class CViewStudentTable : View("Students") {
    val controllerStudents : CServiceStudents by inject()
    init {
        FX.locale = Locale("ru")

    }
    private val students = listOf(
        CStudent(UUID.fromString("b8411849-043b-454c-baa3-8162cb431269"),"Орел Федот Ефимович", "ПМИ-1", Year.parse("2020")),
        CStudent(UUID.fromString("856c727d-f25b-4e17-830d-69f2e11a8ec7"),"Ялчевский Юрин Васильевич", "ПМИ-1", Year.parse("2020")),
        CStudent(UUID.fromString("8751a968-6737-470d-87ae-c1e3b6366508"),"Прилуцкий Герман Тимофеевич", "ПМИ-2", Year.parse("2020")),
        CStudent(UUID.fromString("ab1174fb-49ee-44b2-a6a6-3d3abae2eb1b"),"Колокольцев Марк Елизарович", "ПМИ-2", Year.parse("2020"))
    ).asObservable()

    override val root = tableview(students)
    {
        isEditable = true
        column(messages["ID"],CStudent::propertyId)
        column(messages["FIO"], CStudent::propertyName).makeEditable()
        column(messages["Group"], CStudent::propertyGroup).makeEditable()
        column(messages["Admission_year"], CStudent::propertyYear).makeEditable(CConverterYear)
    }
}