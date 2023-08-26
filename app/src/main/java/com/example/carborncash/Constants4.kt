package com.example.carborncash

object Constants {
    // Arraylist and return the Arraylist
    fun getEmployeeData():ArrayList<Employee>{
        // create an arraylist of type employee class
        val employeeList=ArrayList<Employee>()
        val emp1=Employee("7GB 캐싱 트라이얼","6800₩","7 Days ☆☆☆☆★", R.drawable.seasalt)
        employeeList.add(emp1)
        val emp2=Employee("YouTube 360p 트라이얼","6900₩","10 Days ☆★★★★", R.drawable.brownsugar)
        employeeList.add(emp2)
        val emp3=Employee("5GB 캐싱 트라이얼","6400₩","7 Days ☆☆☆★★", R.drawable.mangolemon)
        employeeList.add(emp3)
        val emp4=Employee("DATA 사용X 트라이얼","6200₩","7 Days ☆★★★★", R.drawable.icejamong)
        employeeList.add(emp4)
        val emp5=Employee("WIFI&DATA 사용X 트라이얼","6400₩","5 Days ★★★★★", R.drawable.coollime)
        employeeList.add(emp5)
        val emp6=Employee("3GB 캐싱 트라이얼","6600₩","7 Days ☆☆★★★", R.drawable.strawberry)
        employeeList.add(emp6)
        val emp7=Employee("폰 사용시간 16h 트라이얼","4700₩","7 Days ☆☆☆★★", R.drawable.decafeine)
        employeeList.add(emp7)
        val emp8=Employee("전화 X 트라이얼","3300₩","7 Days ☆☆★★★", R.drawable.milktealatte)
        employeeList.add(emp8)

        return employeeList
    }
}
