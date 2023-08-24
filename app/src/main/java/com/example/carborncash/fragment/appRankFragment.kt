package com.example.carborncash.fragment

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.TrafficStats
import android.os.Bundle
import android.os.RemoteException
import android.telephony.TelephonyManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentAppRankBinding
import com.example.carborncash.databinding.FragmentMainBinding
import android.app.AppOpsManager
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Process

import android.provider.Settings

import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carborncash.Adapter
import com.example.carborncash.Adapter_App_Rank
import com.example.carborncash.Adapter_User_Rank
import com.example.carborncash.Constants
import com.example.carborncash.Constants4_App_Rank
import com.example.carborncash.Employee_App_Rank
import com.example.carborncash.Employee_User_Rank
import com.example.carborncash.fragment.DataProvider.Companion.changemb
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER D  Compat change id reported: 171228096; UID 10186; state:
/**
 * A simple [Fragment] subclass.
 * Use the [appRankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class appRankFragment : Fragment() {


    private var _binding : FragmentAppRankBinding? = null
    private val binding get() = _binding!!



    // TODO: Rename and change types of paramete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppRankBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val employeeList=ArrayList<Employee_App_Rank>()
        val emp1= Employee_App_Rank("原神", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 41/100) ).toString() + "g", R.drawable.onesin )
        employeeList.add(emp1)
        val emp2= Employee_App_Rank("Youtube", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 23/100) ).toString() + "g", R.drawable.youtube)
        employeeList.add(emp2)
        val emp3= Employee_App_Rank("Instagram", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 17/100) ).toString() + "g", R.drawable.insta)
        employeeList.add(emp3)
        val emp4= Employee_App_Rank("Netflix", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 9/100) ).toString() + "g", R.drawable.neflix)
        employeeList.add(emp4)
        val emp5= Employee_App_Rank("Chrome", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 6/100) ).toString() + "g", R.drawable.crom)
        employeeList.add(emp5)
        val emp6= Employee_App_Rank("Naver", "사용량 : "+((changemb(DataProvider.getYesterdayUsage(requireContext())).toInt() * 11 * 4/100) ).toString() + "g", R.drawable.naver)
        employeeList.add(emp6)

        val itemAdapter= Adapter_App_Rank(employeeList)
        // Set the LayoutManager that
        // this RecyclerView will use.
        val recyclerView: RecyclerView =view.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter


//        view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
//            binding.btnGetDataUsage.visibility = View.GONE
//            val textView = TextView(requireContext())
//            val textView2 = TextView(requireContext())
//            val context = requireContext()
//            textView2.text = DataProvider.getDayUsage(context)
//
//            textView2.textSize = 30f
//
//            textView2.setTypeface(null, Typeface.BOLD)
//
//            textView2.id = 2
//
//            textView.setTextColor(Color.BLACK)
//            val linearLayout = binding.listLayout.getChildAt(0) as LinearLayout
//            linearLayout.addView(textView2)
//
//            textView.text = DataProvider.getUsageSummary(context).toString()
//
//            textView.textSize = 30f
//
//            textView.setTypeface(null, Typeface.BOLD)
//
//            textView.id = 1
//
//            textView.setTextColor(Color.BLACK)
//            linearLayout.addView(textView)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}