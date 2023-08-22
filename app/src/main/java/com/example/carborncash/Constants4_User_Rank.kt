package com.example.carborncash

object Constants4_User_Rank {
    // Arraylist and return the Arraylist
    fun getEmployeeData():ArrayList<Employee>{
        // create an arraylist of type employee class
        val employeeList=ArrayList<Employee>()
        val emp1=Employee("씨솔트 카라멜 콜드 브루","6800", R.drawable.seasalt)
        employeeList.add(emp1)
        val emp2=Employee("브라운 슈가 오트 쉐이큰 에스프레소","6900", R.drawable.brownsugar)
        employeeList.add(emp2)
        val emp3=Employee("망고 용과 레모네이드 스타벅스 리프레셔","6400", R.drawable.mangolemon)
        employeeList.add(emp3)
        val emp4=Employee("아이스 자몽 허니 블랙 티","6200", R.drawable.icejamong)
        employeeList.add(emp4)
        val emp5=Employee("쿨 라임 피지오","6400", R.drawable.coollime)
        employeeList.add(emp5)
        val emp6=Employee("딸기 딜라이트 요거트 블렌디드","6600", R.drawable.strawberry)
        employeeList.add(emp6)
        val emp7=Employee("디카페인 카라멜 마끼야또","4700", R.drawable.decafeine)
        employeeList.add(emp7)
        val emp8=Employee("흑당밀크티라떼","3300", R.drawable.milktealatte)
        employeeList.add(emp8)
        val emp9=Employee("수박 화채 스무디","4500", R.drawable.watermellon)
        employeeList.add(emp9)
        val emp10=Employee("딸기쿠키프라페","3900", R.drawable.strawberrycookie)
        employeeList.add(emp10)
        val emp11=Employee("에스프레소","1500", R.drawable.espresso)
        employeeList.add(emp11)
        val emp12=Employee("청포도에이드","4000", R.drawable.greengraph)
        employeeList.add(emp12)
        val emp13=Employee("완전토마토","1500", R.drawable.verytomato)
        employeeList.add(emp13)
        val emp14=Employee("딸기라떼","1500", R.drawable.strawberrylatte)
        employeeList.add(emp14)
        val emp15=Employee("디아블로 에너지 드링크","3800", R.drawable.energydrink)
        employeeList.add(emp15)

        return employeeList
    }
}
