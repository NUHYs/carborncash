package com.example.carborncash

object Constants4_App_Rank {
    // Arraylist and return the Arraylist
        fun getEmployeeData():ArrayList<Employee_App_Rank>{
        // create an arraylist of type employee class
        val employeeList=ArrayList<Employee_App_Rank>()
        val emp1=Employee_App_Rank("씨솔트 카라멜 콜드 브루","6800", R.drawable.seasalt)
        employeeList.add(emp1)
        val emp2=Employee_App_Rank("브라운 슈가 오트 쉐이큰 에스프레소","6900", R.drawable.brownsugar)
        employeeList.add(emp2)
        val emp3=Employee_App_Rank("망고 용과 레모네이드 스타벅스 리프레셔","6400", R.drawable.mangolemon)
        employeeList.add(emp3)
        val emp4=Employee_App_Rank("아이스 자몽 허니 블랙 티","6200", R.drawable.icejamong)
        employeeList.add(emp4)
        val emp5=Employee_App_Rank("쿨 라임 피지오","6400", R.drawable.coollime)
        employeeList.add(emp5)
        val emp6=Employee_App_Rank("딸기 딜라이트 요거트 블렌디드","6600", R.drawable.strawberry)
        employeeList.add(emp6)
        val emp7=Employee_App_Rank("디카페인 카라멜 마끼야또","4700", R.drawable.decafeine)
        employeeList.add(emp7)
        val emp8=Employee_App_Rank("흑당밀크티라떼","3300", R.drawable.milktealatte)
        employeeList.add(emp8)

        return employeeList
    }
}
