package com.jemy.todoapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jemy.todoapp.data.room.TodoDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
abstract class DatabaseTest {

    lateinit var todoDatabase: TodoDatabase

    @Before
    fun initDb() {
        todoDatabase = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        todoDatabase.close()
    }

}