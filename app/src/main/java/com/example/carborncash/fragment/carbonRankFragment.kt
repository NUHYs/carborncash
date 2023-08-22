package com.example.carborncash.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
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


//    private var _binding : FragmentCarbonRankBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var database : DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentCarbonRankBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        val user = Firebase.auth.currentUser
////        user?.let {
//
//        database = FirebaseDatabase.getInstance().getReference("Users")
//        var myref = database.child("weekt")
//
//
//        val pointListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val pointValue = dataSnapshot.getValue(Int::class.java) ?: 0
//                view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
//                    binding.btnGetDataUsage.visibility = View.GONE
//                    val textView = TextView(requireContext())
//                    val textView2 = TextView(requireContext())
//                    val context = requireContext()
//                    textView2.text = pointValue.toString()
//
//                    textView2.textSize = 30f
//
//                    textView2.setTypeface(null, Typeface.BOLD)
//
//                    textView2.id = 2
//
//                    textView.setTextColor(Color.BLACK)
//                    val linearLayout = binding.listLayout.getChildAt(0) as LinearLayout
//                    linearLayout.addView(textView2)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Failed to read value
//            }
//        }
//        myref.addValueEventListener(pointListener)
//
//
//
//
//
//
//
//
//        }
//    }

}