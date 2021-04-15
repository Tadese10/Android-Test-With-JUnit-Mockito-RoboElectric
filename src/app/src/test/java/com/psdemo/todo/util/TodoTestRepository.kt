package com.psdemo.todo.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository

class TodoTestRepository(
    private val todos: ArrayList<Todo>
) : TodoRepository {

    override fun getAllTodos(): LiveData<List<Todo>> {
        return MutableLiveData(todos)
    }

    override fun insert(todo: Todo) {
        TODO("Not yet implemented")
    }

    override fun toggleTodo(id: String) {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTodosCount(): LiveData<Int> {
        val count =
            todos.count{
                it.dueDate != null &&
                        it.dueDate!! >= System.currentTimeMillis()
            }

        return  MutableLiveData(count)
    }

}