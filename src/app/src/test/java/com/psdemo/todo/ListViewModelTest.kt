package com.psdemo.todo

import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import com.psdemo.todo.util.TodoTestRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ListViewModelTest {

    @get:Rule
    val exceptionRule = ExpectedException.none()

    lateinit var repository: TodoRepository

    lateinit var model: ListViewModel

    @Before
    fun setup() {
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        val todos = ArrayList<Todo>()
        todos.add(
            Todo("1", "Todo 1", null, false, now)
        )
        todos.add(
            Todo("2", "Todo 2", now + day, false, now)
        )
        todos.add(
            Todo("3", "Todo 3", now + day, true, now)
        )
        todos.add(
            Todo("4", "Todo 4", now - day, false, now)
        )

        repository = TodoTestRepository(todos)

        model = ListViewModel(repository)
    }

    @Test
    fun test_allTodos() {
        //Arrange
        val expected = 4

        val todos = model.allTodos.value

        assertNotNull(todos)

        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_upcomingTodoCount() {
        //Arrange
        val expected = 2

        val todoCount = model.upcomingTodosCount.value

        assertNotNull(todoCount)

        assertEquals(expected, todoCount)
    }

    @Test
    fun test_toggleTodo() {//Toggle tod with fake Id
        val id = "fake"

        exceptionRule.expect(NotImplementedError::class.java)

        model.toggleTodo(id)
    }

}