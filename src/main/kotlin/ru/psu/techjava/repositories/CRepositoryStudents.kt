package ru.psu.techjava.repositories


import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import ru.psu.techjava.model.CStudent
import tornadofx.*
import java.util.*

/********************************************************************************************************
 * Репозиторий с запросами к серверу в части списка студентов.                                          *
 * @author Селетков И.П. 2022 1128.                                                                     *
 * @link https://github.com/edvin/tornadofx/wiki/REST-Client                                            *
 *******************************************************************************************************/
class CRepositoryStudents                   : Controller()
{
    private val api                         : Rest by inject()

    val students                            = FXCollections.observableArrayList<CStudent>()

    /****************************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                                *
     ***************************************************************************************************/
    fun getAll()                            : ObservableList<CStudent>
    {
        var response                        : Rest.Response?
                                            = null
        try {
            response                        = api.get("students")
            if (response.ok()) {
                val listFromServer          = response
                    .list()
                    .toModel<CStudent>()
                students.addAll(listFromServer)
                listFromServer.addListener(ListChangeListener { c ->
                        while (c.next()) {
                            if (c.wasAdded()) {
                                students.addAll(c.addedSubList);
                            }
                            if (c.wasRemoved()) {
                                students.removeAll(c.removed.toSet());
                            }
                        }
                    })
                return students
            }
            else if (response.statusCode == 404)
                throw Exception("404")
            else
                throw Exception("getCustomer returned ${response.statusCode} ${response.reason}")
        }
        catch(
            e                               : Exception
        )
        {
            throw Exception("Отсутствует соединение с сервером.", e)
        }
        finally {
            response?.consume()
        }
    }

    /****************************************************************************************************
     * Отправка данных объекта на сервер.                                                               *
     * @param student - объект для отправки.                                                            *
     ***************************************************************************************************/
    fun save(
        student                             : CStudent
    )
    {
        //api.post("students", student)
    }
    /****************************************************************************************************
     * Отправка данных объекта на сервер.                                                               *
     * @param student - объект для отправки.                                                            *
     ***************************************************************************************************/
    fun saveAll()
    {
        val studentsFromServer              = api.get("students")
                .list()
                .toModel<CStudent>()
        var flag = false
        studentsFromServer
            .forEach {studentFromServer ->
                //students.contains(studentFromServer)
                flag = true
                run breaking@ {
                    students.forEach {studentLocal ->
                        if (studentLocal.id==studentFromServer.id)
                            flag = false
                            return@breaking //break
                    }
                }
                if (flag)
                {
                    api.delete("students", studentFromServer)
                }
            }

        students
            .forEach { student->
                api.post("students", student)
            }
    }
    /****************************************************************************************************
     * Отправка данных объекта на сервер.                                                               *
     * @param student - объект для отправки.                                                            *
     ***************************************************************************************************/
    fun add(
        student                             : CStudent
    )
    {
        //api.post("students", student)
        students.add(student)
    }
    /****************************************************************************************************
     * Отправка данных объекта на сервер.                                                               *
     * @param student - объект для отправки.                                                            *
     ***************************************************************************************************/
    fun delete(
        student                             : CStudent
    )
    {
        //api.delete("students", student)
        students.remove(student)
    }
}