package com.example.carborncash.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carborncash.Adapter
import com.example.carborncash.Constants
import com.example.carborncash.Employee
import com.example.carborncash.MainActivity
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentPointStoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [pointStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class pointStoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    private var _binding : FragmentPointStoreBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // getting the employeelist
        val employelist= Constants.getEmployeeData()
        // Assign employeelist to ItemAdapter



        val itemAdapter= Adapter(employelist)

        itemAdapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onItemClick(employee: Employee) {
                val mActivity = activity as MainActivity

                mActivity.replaceFragment(PurchaseFragment())
            }
        })
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)


        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(gridLayoutManager)

        // adapter instance is set to the
        // recyclerview to inflate the items.

        recyclerView.adapter = itemAdapter




        val user = Firebase.auth.currentUser
        user?.let {
            database = FirebaseDatabase.getInstance().getReference("Users")
            var myref = database.child(it.email!!.toString().replace('.', '_')).child("point")

            binding.progressbar.visibility = View.VISIBLE

            val pointListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val pointValue = dataSnapshot.getValue(Int::class.java) ?: 0
                    binding.cash.text = "C : " + pointValue.toString()
                    binding.progressbar.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Failed to read value
                }
            }
            myref.addValueEventListener(pointListener)

        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPointStoreBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pointStoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pointStoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}