package com.example.demo.view

import com.example.demo.model.CStudent
import javafx.stage.StageStyle
import tornadofx.*
import java.util.*

class MainView : View("Hello TornadoFX") {

//    private val label = label("Введите текст!")
//    private val tfText = textfield("")
//    private val btn = button("Показать сообщение") {
//        action {
//            find<CMessage>(
//                mapOf(
//                    CMessage::text1 to tfText.text,
//                    CMessage::text2 to "adawdawd"
//                )
//            ).openModal(stageStyle = StageStyle.UTILITY)
//            //println(tfText.text)
//        }
//    }
    private val students = listOf(
        CStudent(UUID.fromString("b8411849-043b-454c-baa3-8162cb431269"),"Samantha Stuart"),
        CStudent(UUID.fromString("856c727d-f25b-4e17-830d-69f2e11a8ec7"),"Tom Marks"),
        CStudent(UUID.fromString("8751a968-6737-470d-87ae-c1e3b6366508"),"Stuart Gills"),
        CStudent(UUID.fromString("ab1174fb-49ee-44b2-a6a6-3d3abae2eb1b"),"Nicole Williams")
    ).asObservable()

    override val root = tableview(students)
    {
        isEditable = true
        //column("ID",CStudent::idProperty).makeEditable()
        column("Name", CStudent::nameProperty).makeEditable()
    }




}