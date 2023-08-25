package com.example.carborncash.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentMainBinding
import com.example.carborncash.fragment.DataProvider.Companion.changemb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class DailyExecutionManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    fun shouldExecute(): Boolean {
        val currentDate = Calendar.getInstance()
        val lastExecutionDateMillis = sharedPreferences.getLong("lastExecutionDate", 0)
        val lastExecutionDate = Calendar.getInstance().apply { timeInMillis = lastExecutionDateMillis }

        return currentDate.get(Calendar.DAY_OF_YEAR) != lastExecutionDate.get(Calendar.DAY_OF_YEAR)
    }

    fun markExecuted() {
        val currentDate = Calendar.getInstance()
        sharedPreferences.edit().putLong("lastExecutionDate", currentDate.timeInMillis).apply()
    }
}

class DailyExecutionManager2(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs2", Context.MODE_PRIVATE)

    fun shouldExecute2(): Boolean {
        val currentDate = Calendar.getInstance()
        val lastExecutionDateMillis = sharedPreferences.getLong("lastExecutionDate2", 0)
        val lastExecutionDate = Calendar.getInstance().apply { timeInMillis = lastExecutionDateMillis }

        return currentDate.get(Calendar.DAY_OF_YEAR) != lastExecutionDate.get(Calendar.DAY_OF_YEAR)
    }

    fun markExecuted2() {
        val currentDate = Calendar.getInstance()
        sharedPreferences.edit().putLong("lastExecutionDate2", currentDate.timeInMillis).apply()
    }
}


class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root



    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var energe = ((changemb(DataProvider.getDayUsage(requireContext())).toInt()) * 2) / 100



        binding.carbonProgress.progress = energe
        updateSpecificData()





        val user = Firebase.auth.currentUser
        user?.let {
            database = FirebaseDatabase.getInstance().getReference("Users")
            var myref = database.child(it.email!!.toString().replace('.', '_')).child("point")

            binding.progressBar.visibility = View.VISIBLE

            val pointListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val pointValue = dataSnapshot.getValue(Int::class.java) ?: 0
                    binding.point.text = "보유 포인트 : "+ pointValue.toString()
                    binding.progressBar.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Failed to read value
                }
            }
            myref.addValueEventListener(pointListener)

            binding.recivebtn.setOnClickListener(){

                val dailyExecutionManager2 = DailyExecutionManager2(requireContext())
                if (dailyExecutionManager2.shouldExecute2()) {
                    myref.runTransaction(object : Transaction.Handler {
                        override fun doTransaction(mutableData: MutableData): Transaction.Result {
                            val currentValue = mutableData.getValue(Int::class.java) ?: 0
                            mutableData.value = currentValue + ((5000 - changemb(DataProvider.getYesterdayUsage(requireContext())).toInt()) / 50)
                            return Transaction.success(mutableData)

                        }

                        override fun onComplete(
                            databaseError: DatabaseError?,
                            committed: Boolean,
                            currentData: DataSnapshot?
                        ) {
                            // Transaction completed
                        }
                    })
                    dailyExecutionManager2.markExecuted2()
                }

            }

            Glide.with(this /* context */)
                .load(it.photoUrl)
                .into(binding.profileimg)

        }

        binding.compare.text = "어제 사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11)).toString() + "g"

        binding.compare2.text = "오늘 사용량 : "+((changemb(DataProvider.getDayUsage(requireContext())).toInt() * 11)).toString() + "g"

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun updateSpecificData() {
        
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val newDataValue = changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() // 업데이트할 데이터 값

        val user = Firebase.auth.currentUser
        user?.let {
            // 업데이트할 데이터 경로 설정
            val dataRef: DatabaseReference = firebaseDatabase.reference.child("Users").child(it.email!!.toString().replace('.', '_')).child("ydayc")

            // 데이터 업데이트
            dataRef.setValue(newDataValue)
                .addOnSuccessListener {
                    println("데이터 업데이트 성공")
                }
                .addOnFailureListener { error ->
                    println("데이터 업데이트  에러: $error")
                }
        }
    }

    fun weekadd() {

        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> {
                updateweekadd()
            }
            DayOfWeek.TUESDAY -> {
                updateweekaddtwo()
                updateweekadd()
            }
            else -> {
                updateweekadd()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        updateusage()
        val dailyExecutionManager = DailyExecutionManager(requireContext())

        if (dailyExecutionManager.shouldExecute()) {
            // 하루에 딱 한 번만 실행되어야 하는 코드 실행
            weekadd()
            dailyExecutionManager.markExecuted()

        }


    }

    fun updateweekadd() {
        val user = Firebase.auth.currentUser
        user?.let {
            database = FirebaseDatabase.getInstance().getReference("Users")
            var myref = database.child(it.email!!.toString().replace('.', '_')).child("weekt")


            myref.runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    val currentValue = mutableData.getValue(Int::class.java) ?: 0
                    mutableData.value = currentValue + changemb(DataProvider.getYesterdayUsage(requireContext())).toInt()
                    return Transaction.success(mutableData)

                }

                override fun onComplete(
                    databaseError: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    // Transaction completed
                }
            })
        }
    }

    fun updateweekaddtwo() {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val newDataValue = 0 // 업데이트할 데이터 값

        val user = Firebase.auth.currentUser
        user?.let {
            // 업데이트할 데이터 경로 설정
            val dataRef: DatabaseReference = firebaseDatabase.reference.child("Users").child(it.email!!.toString().replace('.', '_')).child("weekt")

            // 데이터 업데이트
            dataRef.setValue(newDataValue)
                .addOnSuccessListener {
                    println("데이터 업데이트 성공")
                }
                .addOnFailureListener { error ->
                    println("데이터 업데이트  에러: $error")
                }
        }
    }

}