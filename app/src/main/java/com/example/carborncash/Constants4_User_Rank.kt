package com.example.carborncash

import android.os.Bundle
import android.view.View

object Constants4_User_Rank {

    // Arraylist and return the Arraylist
    fun getEmployeeData(useremail: String, weekt: String):ArrayList<Employee_User_Rank>{
        // create an arraylist of type employee class
        val employeeList=ArrayList<Employee_User_Rank>()
        val emp1=Employee_User_Rank(useremail, weekt)
        employeeList.add(emp1)

        return employeeList
    }
}
