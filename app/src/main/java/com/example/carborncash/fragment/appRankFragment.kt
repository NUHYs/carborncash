package com.example.carborncash.fragment

import android.annotation.SuppressLint
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.PackageManager
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [appRankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class appRankFragment : Fragment() {

    lateinit var navController: NavController

    private var _binding : FragmentAppRankBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = requireContext()

        fun getSubscriberId(networkType: Int): String {
            val context = requireContext()
            if (ConnectivityManager.TYPE_MOBILE == networkType) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                return tm.subscriberId
            }
            return ""
        }

        super.onCreate(savedInstanceState)

        val networkStatsManager = requireActivity().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
        var bucket: NetworkStats.Bucket

        try {
            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI, "", 0, System.currentTimeMillis())
            val wifiUsage = bucket.rxBytes + bucket.txBytes

            bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE, getSubscriberId(ConnectivityManager.TYPE_MOBILE), 0, System.currentTimeMillis())
            val mobileUsage = bucket.rxBytes + bucket.txBytes

            binding.apprank.text = "WiFi usage: $wifiUsage\nMobile usage: $mobileUsage"
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}