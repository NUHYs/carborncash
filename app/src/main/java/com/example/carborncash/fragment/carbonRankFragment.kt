package com.example.carborncash.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentAppRankBinding
import com.example.carborncash.databinding.FragmentCarbonRankBinding
import com.example.carborncash.databinding.FragmentMainBinding
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
class carbonRankFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private var _binding: FragmentCarbonRankBinding? = null
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
        _binding = FragmentCarbonRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val user = Firebase.auth.currentUser
//        user?.let {

        database = FirebaseDatabase.getInstance().reference

        view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
            retrieveAndSortData()
        }


//        view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
//            usersRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val userList = mutableListOf<User>()
//
//                    for (userSnapshot in dataSnapshot.children) {
//                        val user = userSnapshot.getValue(User::class.java)
//                        if (user != null) {
//                            userList.add(user)
//                        }
//                    }
//
//
//                    binding.btnGetDataUsage.visibility = View.GONE
//                    val textView2 = TextView(requireContext())
//                    textView2.text = userList.sortBy { it.weekt }.toString()
//
//                    textView2.textSize = 30f
//
//                    textView2.setTypeface(null, Typeface.BOLD)
//
//                    textView2.setTextColor(Color.BLACK)
//                    val linearLayout = binding.listLayout.getChildAt(0) as LinearLayout
//                    linearLayout.addView(textView2)
//
//
//                    // 정렬된 데이터를 사용하여 작업을 수행하세요.
//                    // userList를 RecyclerView 등에 바인딩하거나 원하는대로 활용할 수 있습니다.
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // 데이터베이스 읽기 실패 시 처리할 내용을 여기에 추가하세요.
//                }
//            })

    }


    private fun retrieveAndSortData() {
        val usersRef = database.child("Users")
        val query = usersRef.orderByChild("weekt")

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
                userList.sortByDescending { it.weekt }


                // 정렬된 데이터를 화면에 표시하는 로직 구현
                // 예: RecyclerView나 ListView 사용

                // 여기서 userList를 화면에 표시하는 로직을 구현하면 됩니다.
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
