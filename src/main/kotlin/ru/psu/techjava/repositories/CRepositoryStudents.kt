package ru.psu.techjava.repositories

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import ru.psu.techjava.model.CStudent
import tornadofx.*

/********************************************************************************************************
 * Репозиторий с запросами к серверу в части списка студентов.                                          *
 * @author Селетков И.П. 2022 1128.                                                                     *
 * @link https://github.com/edvin/tornadofx/wiki/REST-Client                                            *
 *******************************************************************************************************/
class CRepositoryStudents                   : Controller()
{
    //Объект для отправки запросов к API сервера.
    private val api                         : Rest by inject()
    //"Кэшированный" список студентов для локальной работы.
    private val students                    = FXCollections.observableArrayList<CStudent>()

    /****************************************************************************************************
     * Запрос списка всех доступных объектов на сервере.                                                *
     ***************************************************************************************************/
    fun getAll()                            : ObservableList<CStudent>
    {
        //Запрашиваем актуальные данные на сервере
        val listFromServer                  = requestAll()
        //Пересохраняем в локальный список,
        //в котором дополнительно будем кэшировать изменения в процессе
        //редактирования таблицы.
        students.addAll(listFromServer)
        //Возвращаем ссылку на кэш.
        return students
    }
    /****************************************************************************************************
     * Запрос списка студентов на сервере и обработка возможных проблем.                                *
     ***************************************************************************************************/
    private fun requestAll()                : ObservableList<CStudent>
    {
        var response                        : Rest.Response?
                                            = null
        try {
            //Собственно выполнение GET запроса к пути /students
            response                        = api.get("students")
            if (response.ok()) {
                //Конвертация json в список объектов типа CStudent
                return response
                    .list()
                    .toModel()
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
     * Изменение студента в локальном списке.                                                           *
     * @param student - объект для отправки.                                                            *
     ***************************************************************************************************/
    fun save(
        student                             : CStudent
    )
    {
        //Не стали делать отправку, т.к. приняли централизованно все изменения для списка отправлять вмсте.
        //api.post("students", student)
    }
    /****************************************************************************************************
     * Отправка списка объектов на сервер.                                                              *
     ***************************************************************************************************/
    fun saveAll()
    {
        //Запрос данных с сервера.
        val studentsFromServer              = requestAll()

        //Перебираем всех студентов с сервера,
        //ищем тех, кто отсутствует в локальном списке,
        //для них отправляем запрос на удаление.
        studentsFromServer
            .forEach { studentFromServer ->
                //Для того, чтобы метод contains сработал правильно,
                //и чтобы не писать ручной перебор,
                //переопределён метод CStudent.equals
                if (!students.contains(studentFromServer))
                {
                    api.delete("students", studentFromServer)
                }
            }
        //Перебираем все записи из локального кэша
        var temp                            : List<CStudent>
        students
            .forEach { studentLocal->
                //Для каждой локальной записи находим записи из сервера с такими же id.
                temp                        = studentsFromServer
                    .filter { studentFromServer ->
                        studentLocal.equals(studentFromServer)
                    }
                //Записи с такими же id
                // (должна быть максимум 1, но гарантий никто не даст)
                // фильтруем по наличию изменений
                temp
                    .firstOrNull { studentFromServer ->
                        studentLocal.hasChanges(studentFromServer)
                    }
                    //Если изменения в полях есть, публикуем текущую запись на сервер.
                    ?.let {
                        api.post("students", studentLocal)
                    }
                //Если вообще на сервере записей с таким id нет
                //публикуем текущую запись.
                if (temp.isEmpty())
                    api.post("students", studentLocal)

            }
    }
    /****************************************************************************************************
     * Добвление студента в локальный список.                                                           *
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
     * Удаление студента из локального списка.                                                          *
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