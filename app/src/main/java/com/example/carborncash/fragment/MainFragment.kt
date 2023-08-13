package com.example.carborncash.fragment

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentMainBinding
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



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

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)



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

                myref.runTransaction(object : Transaction.Handler {
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        val currentValue = mutableData.getValue(Int::class.java) ?: 0
                        mutableData.value = currentValue + 1
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

            binding.profile.text = it.email
            Glide.with(this /* context */)
                .load(it.photoUrl)
                .into(binding.profileimg)

        }
        binding.btnNextPointStore.setOnClickListener(){
            findNavController().navigate(R.id.action_mainFragment_to_totalFragment3)
        }
        binding.btnNextAppRank.setOnClickListener(){
            findNavController().navigate(R.id.action_mainFragment_to_appRankFragment)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}