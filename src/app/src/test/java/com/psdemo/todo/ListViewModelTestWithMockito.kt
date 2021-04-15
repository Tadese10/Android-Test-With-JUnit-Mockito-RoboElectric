package com.psdemo.todo

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.psdemo.todo.data.Todo
import com.psdemo.todo.data.TodoRepository
import com.psdemo.todo.list.ListViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalArgumentException

class ListViewModelTestWithMockito {

    @get:Rule
    val exceptionRule = ExpectedException.none()

    lateinit var model: ListViewModel

    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    @Test
    fun test_allTodosEmpty() {
        //Arrange
        val expected = 0
        val repository : TodoRepository = mock()
        whenever(repository.getAllTodos()).
            thenReturn(MutableLiveData(arrayListOf()))

        model = ListViewModel(repository)

        val todos = model.allTodos.value

        assertNotNull(todos)

        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_allTodosSingle() {
        //Arrange
        val expected = 1
        val repository : TodoRepository = mock()
        //Act
        whenever(repository.getAllTodos()).
        thenReturn(MutableLiveData(arrayListOf(
            Todo("4", "Todo 4", now - day, false, now)
        )))

        model = ListViewModel(repository)

        val todos = model.allTodos.value

        assertNotNull(todos)

        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_allTodosMultiple() {
        //Arrange
        val expected = 3
        val repository : TodoRepository = mock()
        //Act
        whenever(repository.getAllTodos()).
        thenReturn(MutableLiveData(arrayListOf(
            Todo("4", "Todo 4", now - day, false, now),
            Todo("2", "Todo 2", now + day, false, now),
            Todo("1", "Todo 1", null, false, now)
        )))

        model = ListViewModel(repository)

        val todos = model.allTodos.value

        assertNotNull(todos)

        assertEquals(expected, todos!!.size)
    }

    @Test
    fun test_upcomingTodoCountEmpty() {
        //Arrange
        val expected = 0

        val repository : TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount()).
        thenReturn(MutableLiveData(expected))

        model = ListViewModel(repository)

        val todoCount = model.upcomingTodosCount.value

        assertNotNull(todoCount)

        assertEquals(expected, todoCount)
    }

    @Test
    fun test_upcomingTodoCountSingle() {
        //Arrange
        val expected = 1

        val repository : TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount()).
        thenReturn(MutableLiveData(expected))

        model = ListViewModel(repository)

        val todoCount = model.upcomingTodosCount.value

        assertNotNull(todoCount)

        assertEquals(expected, todoCount)
    }

    @Test
    fun test_upcomingTodoCountMultiple() {
        //Arrange
        val expected = 5

        val repository : TodoRepository = mock()
        whenever(repository.getUpcomingTodosCount()).
        thenReturn(MutableLiveData(expected))

        model = ListViewModel(repository)

        val todoCount = model.upcomingTodosCount.value

        assertNotNull(todoCount)

        assertEquals(expected, todoCount)
    }

    @Test
    fun test_toggleTodo() {//Toggle tod with fake Id
        val repository: TodoRepository = mock();
        val id = "fake"
        model = ListViewModel(repository)

        model.toggleTodo(id)

        verify(repository).toggleTodo(id)
    }

    @Test
    fun test_toggleTodoNotFound() {//Toggle tod with fake Id
        val repository: TodoRepository = mock();
        val exceptionMessage = "Todo not found"
        val id = "fake"
        whenever(repository.toggleTodo(id))
            .thenThrow(IllegalArgumentException(exceptionMessage))
        model = ListViewModel(repository)

        exceptionRule.expect(IllegalArgumentException::class.java)
        exceptionRule.expectMessage(exceptionMessage)

        model.toggleTodo(id)

        verify(repository).toggleTodo(id)
    }

}