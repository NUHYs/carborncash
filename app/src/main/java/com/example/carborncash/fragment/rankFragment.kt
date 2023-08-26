package com.example.carborncash.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carborncash.Adapter
import com.example.carborncash.Adapter_User_Rank
import com.example.carborncash.Constants
import com.example.carborncash.Constants4_User_Rank
import com.example.carborncash.Employee_User_Rank
import com.example.carborncash.MainActivity
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentAppRankBinding
import com.example.carborncash.databinding.FragmentCarbonRankBinding
import com.example.carborncash.databinding.FragmentMainBinding
import com.example.carborncash.databinding.FragmentRankBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [carbonRankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class rankFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference

        binding.user.setOnClickListener(){
            val mActivity = activity as MainActivity
            mActivity.replaceFragment(carbonRankFragment())
        }

        retrieveAndSortData(view)

//        val user = Firebase.auth.currentUser
//        user?.let {


    }


    private fun retrieveAndSortData(view: View) {
        val usersRef = database.child("Users")
        val query = usersRef.orderByChild("point")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<User>()

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let {
                        userList.add(it)
                    }
                }

                // weekt 값에 따라 내림차순으로 정렬
                userList.sortBy { it.weekt }


                val employeeList=ArrayList<Employee_User_Rank>()
                val emp1= Employee_User_Rank("1. "+userList[0].useremail, "24 Trial")
                employeeList.add(emp1)
                val emp2= Employee_User_Rank("2. "+userList[1].useremail, "22 Trial")
                employeeList.add(emp2)
                val emp3= Employee_User_Rank("3. "+userList[2].useremail, "17 Trial")
                employeeList.add(emp3)
                val emp4= Employee_User_Rank("4. "+userList[3].useremail, "13 Trial")
                employeeList.add(emp4)
                val emp5= Employee_User_Rank("5. "+userList[4].useremail, "8 Trial")
                employeeList.add(emp5)
                val emp6= Employee_User_Rank("6. "+userList[5].useremail, "2 Trial")
                employeeList.add(emp6)

                // Assign employeelist to ItemAdapter
                val itemAdapter= Adapter_User_Rank(employeeList)
                // Set the LayoutManager that
                // this RecyclerView will use.
                val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
                recyclerView.layoutManager = LinearLayoutManager(context)
                // adapter instance is set to the
                // recyclerview to inflate the items.
                recyclerView.adapter = itemAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Firebase", "Data retrieval cancelled: ${databaseError.message}")
            }
        })
    }
    data class User(
        val useremail: String = "",
        val point: Int = 0,
        val weekt: Int = 0,
        val ydayc: Int = 0,
    )

}
