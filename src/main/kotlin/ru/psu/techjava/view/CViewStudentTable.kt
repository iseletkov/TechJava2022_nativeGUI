package ru.psu.techjava.view

import javafx.scene.layout.BorderPane
import ru.psu.techjava.model.CStudent
import ru.psu.techjava.util.convertes.CConverterYear
import ru.psu.techjava.viewmodels.CViewModelStudent
import ru.psu.techjava.viewmodels.CViewModelStudentList
import tornadofx.*
/********************************************************************************************************
 * Страница со списком студентов.                                                                       *
 * @author Селетков И.П. 2022 1121.                                                                     *
 *******************************************************************************************************/
class CViewStudentTable                     : View("Students")
{
    //Корневой элемент формы - элемент с 5ю областями (Верх, Низ, Левая, Правая, Центр)
    override val root                       = BorderPane()
    //Модель представления для списка в целом.
    private val viewModelList               : CViewModelStudentList by inject()
    //Модель представления для одного элемента,
    //отображаемого в правой боковой панели редактирвоания.
    val viewModelItem                       = CViewModelStudent(CStudent())

    //Таблица студентов.
    //В качестве параметра передаём список студентов из модели представления.
    val table                               = tableview(viewModelList.students)
    {
        isEditable                          = true
        column(messages["ID"],CStudent::propertyId)
        column(messages["FIO"], CStudent::propertyName).makeEditable()
        column(messages["Group"], CStudent::propertyStudyGroup).makeEditable()
        column(messages["Admission_year"], CStudent::propertyYear).makeEditable(CConverterYear)

        //Изменение веделения предаём в модель представления правой формы.
        viewModelItem.rebindOnChange(this) { selectedItem ->
            item                            = selectedItem ?: CStudent()
        }
        //Изменение веделения передаём в модель представления всего списка.
        onSelectionChange {
            viewModelList.onSelectionChange(this.selectedItem)
        }
        //Двойной клик
        onDoubleClick {
            //Если есть выделение
            this.selectedItem?.let{
                //Открываем второе окно со списком оценок,
                //передаём туда выделенного студента.

                //Это пример создания дочернего окна, исходное окно остаётся доступным для пользователя.
                //find<CViewMarkList>(mapOf(CViewMarkList::student to this.selectedItem)).openWindow()

                //Это пример замены содержимого в рамках текущего окна.
                replaceWith(find<CViewMarkList>(mapOf(CViewMarkList::student to this.selectedItem)))
            }
        }
    }

    init {
        //Верхняя часть окна
        root.top{
            //Делится на 2 строки
            vbox {
                //Самая верхняя строка - меню.
                menubar {
                    menu("Окна") {
                        item("Оценки").action{
                            //Пример замены содержимого окна на другую View
                            replaceWith<CViewMarkList>()
                        }
                    }
                }
                //Вторая строчка сверху - кнопки для работы со списком объектов.
                hbox {
                    button(messages["Add"]) {
                        action{
                            viewModelList.add()
                        }
                    }
                    button(messages["Delete"]) {
                        //Активность кнопки удалить зависит от поля в модели представления.
                        enableWhen(viewModelList.elementSelected)
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
        }
        root.center{
            //В центральной части располагается таблица.
            this                            += table
        }
        root.right  {
            //В правой части располагается форма для редактирования одного объекта.
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