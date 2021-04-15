package com.psdemo.todo

import com.psdemo.todo.list.determineCardColor
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)//Using Parametrized JUnit Test Runner
class ListUtilsTest(
    private val expectedColor : Int,
    private val dueDate : Long?,
    private val done: Boolean,
    private val scenario: String
){

    companion object{
        val now = System.currentTimeMillis()
        val day = 1000 * 60 * 60 * 24

        @JvmStatic
        @Parameterized.Parameters(name = "determineCardColor: {3}")//It displays the fourth item in the list as the name of the test scenario. NB: It zero based index
        fun todos() = listOf(
            arrayOf(R.color.todoDone, null, true, "Done, no date"),
            arrayOf(R.color.todoNotDue, null, false, "Not done, no date"),
            arrayOf(R.color.todoOverDue, now - day, false, "Not done, due yesterday"),
            arrayOf(R.color.todoOverDue, now - (day * 2), false, "Not done, due two days ago")
        )
    }

    @Test
    fun test_determineCardColor(){

        //Arrange

        //Act
        val actual = determineCardColor(dueDate, done)


        //Assert
        assertEquals(expectedColor, actual)
    }

//    @Test
//    fun test_determineCardColorNotDone(){
//
//        //Arrange
//        val expectedColor = R.color.todoNotDue
//        val dueDate = null
//        val done  = false
//
//
//        //Act
//        val actual = determineCardColor(dueDate, done)
//
//
//        //Assert
//        assertEquals(expectedColor, actual)
//    }
//
//    @Test
//    fun test_determineCardColorOverDue(){
//
//        //Arrange
//        val expectedColor = R.color.todoOverDue
//        val dueDate = now - day
//        val done  = false
//
//
//        //Act
//        val actual = determineCardColor(dueDate, done)
//
//
//        //Assert
//        assertEquals(expectedColor, actual)
//    }
}