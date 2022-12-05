package ru.psu.techjava.view

import javafx.scene.layout.BorderPane
import ru.psu.techjava.model.CStudent
import ru.psu.techjava.util.convertes.CConverterYear
import ru.psu.techjava.viewmodels.CViewModelStudent
import ru.psu.techjava.viewmodels.CViewModelStudentList
import tornadofx.*

class CViewStudentTable                     : View("Students")
{
    override val root                       = BorderPane()
    private val viewModelList               : CViewModelStudentList by inject()
    val viewModelItem                       = CViewModelStudent(CStudent())

    val table                               = tableview(viewModelList.students)
    {
        isEditable                          = true
        column(messages["ID"],CStudent::propertyId)
        column(messages["FIO"], CStudent::propertyName).makeEditable()
        column(messages["Group"], CStudent::propertyStudyGroup).makeEditable()
        column(messages["Admission_year"], CStudent::propertyYear).makeEditable(CConverterYear)

        viewModelItem.rebindOnChange(this) { selectedItem ->
            item                            = selectedItem ?: CStudent()
        }
        onSelectionChange {
            viewModelList.setTableSelection(this.selectedItem)
        }
    }

    init {
        root.top{
            hbox {
                button(messages["Add"]) {
                    action{
                        viewModelList.add()
                    }
                }
                button(messages["Delete"]) {
                    enableWhen(viewModelList.deleteEnabled)
                    action{
                        viewModelList.delete(table.selectedItem)
                    }
                }
                button(messages["Save"]) {
                    action{
                        viewModelList.saveAll()
                    }
                }
            }

        }
        root.center{
            this                            += table
        }
        root.right  {
            form{
                fieldset(messages["Edit_student"]) {
                    field(messages["FIO"]) {
                        textfield(viewModelItem.name)
                    }
                    field(messages["Group"]) {
                        textfield(viewModelItem.studyGroup)
                    }
                    hbox{
                        button(messages["Save"]) {
                            enableWhen(viewModelItem.dirty)
                            action{
                                viewModelItem.save()
                            }
                        }
                        button(messages["Cancel"]).action {
                            viewModelItem.rollback()
                        }

                    }
                }
            }
        }
    }

}