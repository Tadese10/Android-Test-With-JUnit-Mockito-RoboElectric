package com.psdemo.todo

import com.nhaarman.mockitokotlin2.*
import com.psdemo.todo.add.AddViewModel
import com.psdemo.todo.data.TodoRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Test

class AddViewModelTest {

    @Test
    fun test_saveWithTitleWithoutDate(){
        //Arrange
        var repository: TodoRepository = mock()
        var model = AddViewModel(repository)
        val actualTitle = "Test todo"
        model.todo.title = actualTitle

        //Act
        val actual = model.save()


        assertNull(actual)

        verify(repository).insert(
            argThat{
                title == actualTitle && dueDate == null
            }
        )

    }

    @Test
    fun test_saveNoTitle(){
        //Arrange
        var repository: TodoRepository = mock()
        var model = AddViewModel(repository)
        val expected = "Title is required"

        //Act
        val actual = model.save()


        assertEquals(expected,actual)

        verify(repository, never()).insert(any())// Verifies that the insert method is not called

    }

    @Test
    fun test_saveWithDate(){
        //Arrange
        var repository: TodoRepository = mock()
        var model = AddViewModel(repository)
        val currentTimeMillis = System.currentTimeMillis()
        model.todo.dueDate = currentTimeMillis
        val actualTitle = "Test todo"
        model.todo.title = actualTitle

        //Act
        val actual = model.save()


        assertNull(actual)

        verify(repository).insert(
            argThat{
                title == actualTitle && dueDate == currentTimeMillis
            }
        )

    }
}