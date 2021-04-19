package com.psdemo.todo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import com.psdemo.todo.data.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.lang.Exception
import java.lang.RuntimeException

@RunWith(AndroidJUnit4::class)
class TodoRoomRepositoryTest {


    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @get:Rule
    val exceptionRule = ExpectedException.none()

    val now = System.currentTimeMillis()
    val day = 1000 * 60 * 60 * 24

    private lateinit var db: TodoRoomDatabase

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()//This comes from androidx test library and it allows us to access the Test Context

        db = Room.inMemoryDatabaseBuilder(context, TodoRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun test_getUpComingTodoCountyEmpty(){
        //Arrange
        val dao = spy(db.todoDao())
        val repo = TodoRoomRepository(dao)
        val expected = 0

        //Act
        val actual = repo.getUpcomingTodosCount().test().value()


        //Assert
        assertEquals(expected, actual)
        verify(dao).getDateCount(any())
    }

    @Test
    fun test_getUpComingTodoCountySingleMatch(){
        //Arrange
        val dao = spy(db.todoDao())
        dao.insert(Todo("4", "Todo 4", now - day, false, now))
        dao.insert(Todo("2", "Todo 2", now + day, false, now))
        dao.insert(Todo("1", "Todo 1", null, false, now))
        val repo = TodoRoomRepository(dao)
        val expected = 1

        //Act
        val actual = repo.getUpcomingTodosCount().test().value()


        //Assert
        assertEquals(expected, actual)
        verify(dao).getDateCount(any())
    }

    @Test
    fun test_getAllTodosMultiple(){
        //Arrange
        val dao = spy(db.todoDao())
        val testTodo = Todo("4", "Todo 4", now - day, false, now)
        dao.insert(testTodo)
        dao.insert(Todo("2", "Todo 2", now + day, false, now))
        dao.insert(Todo("1", "Todo 1", null, false, now))
        val repo = TodoRoomRepository(dao)
        val expected = 3

        //Act
        val actual = repo.getAllTodos().test().value()


        //Assert
        assertEquals(expected, actual.size)
        verify(dao).getAllTodos()
        assertTrue(actual.contains(testTodo))
    }

    @Test
    fun test_toggleTodoGoodId(){
        val dao = mock<TodoDao>{
            on(it.toggleTodo(any()))
                .doAnswer{
                    val id = it.arguments[0]
                    require(id != "bad"){"bad id"}
                    1
                }
        }

        val repo = TodoRoomRepository(dao)
        val id = "good"

        repo.toggleTodo(id)


        verify(dao).toggleTodo(id)

    }

    @Test
    fun test_toggleTodoBadId(){
        val dao = mock<TodoDao>{
            on(it.toggleTodo(any()))
                .doAnswer{
                    val id = it.arguments[0]
                    require(id != "bad"){"bad id"}
                    1
                }
        }

        val repo = TodoRoomRepository(dao)
        val id = "bad"

        exceptionRule.expect(RuntimeException::class.java)

        repo.toggleTodo(id)


        verify(dao).toggleTodo(id)

    }

    @Test
    fun test_insert(){
        val dao = mock<TodoDao>()
        val repo = TodoRoomRepository(dao)
        val expected = Todo("1", "Todo 1", null, false, now)

        repo.insert(expected)

        argumentCaptor<Todo>()
            .apply {
                verify(dao).insert(capture())

                assertEquals(expected, firstValue)
            }
    }


    @After
    fun teardown(){
        db.close()//To clear the in memory database
    }
}