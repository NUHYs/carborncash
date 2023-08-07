package com.example.carborncash.fragment

<<<<<<< HEAD
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

=======
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
import android.net.ConnectivityManager
import android.net.TrafficStats
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.carborncash.R
import com.example.carborncash.databinding.FragmentAppRankBinding
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.util.Calendar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER D  Compat change id reported: 171228096; UID 10186; state:
>>>>>>> cc0e503 (Initial commit)
/**
 * A simple [Fragment] subclass.
 * Use the [appRankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class appRankFragment : Fragment() {

    lateinit var navController: NavController

<<<<<<< HEAD
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
=======
    private var _binding: FragmentAppRankBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val REQUEST_USAGE_ACCESS = 101
>>>>>>> cc0e503 (Initial commit)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppRankBinding.inflate(inflater, container, false)
        return binding.root
    }

<<<<<<< HEAD
=======
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnPreMain.setOnClickListener() {
            findNavController().navigate(R.id.action_appRankFragment_to_mainFragment)
        }

        view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
            binding.btnGetDataUsage.visibility = View.GONE
            if (!checkUsageStatsPermission()) {
                requestUsageStatsPermission()
            } else {
                getDataUsageFromOtherApp()
            }
        }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOps = context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
        val mode = context?.packageName?.let {
            appOps?.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                it
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivityForResult(intent, REQUEST_USAGE_ACCESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_USAGE_ACCESS) {
            if (checkUsageStatsPermission()) {
                getDataUsageFromOtherApp()
            }
        }
    }

    private fun getDataUsageFromOtherApp() {
        // 어제 데이터 사용량 로그로 출력
        logAppDataUsage()

        // 어제 와이파이 사용량 로그로 출력
        logWiFiUsage()
    }

    private fun logAppDataUsage() {
        val context = requireContext().applicationContext

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1) // 어제로 설정
        val endTime = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_MONTH, -1) // 그제로 설정
        val startTime = calendar.timeInMillis

        val networkStatsManager =
            context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val appDataUsage = networkStatsManager.querySummaryForDevice(
            ConnectivityManager.TYPE_WIFI, "", startTime, endTime
        ).rxBytes + networkStatsManager.querySummaryForDevice(
            ConnectivityManager.TYPE_WIFI, "", startTime, endTime
        ).txBytes

        logToConsole("어제 앱 데이터 사용량: $appDataUsage 바이트")
    }

    private fun logWiFiUsage() {
        val context = requireContext().applicationContext

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1) // Yesterday
        val endTime = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_MONTH, -1) // The day before yesterday
        val startTime = calendar.timeInMillis

        val networkStatsManager =
            context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val bucket = NetworkStats.Bucket()
        val networkStats = networkStatsManager.querySummary(
            ConnectivityManager.TYPE_WIFI, "", startTime, endTime
        )

        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        // Get the list of apps with usage stats for the specified time period
        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        if (usageStatsList.isEmpty()) {
            logToConsole("No app WiFi usage data found for the specified time period.")
            return
        }

        // Create a map to store package name -> app name mapping
        val packageToAppNameMap = mutableMapOf<String, String>()

        // Query package name -> app name mapping
        for (usageStats in usageStatsList) {
            try {
                val packageInfo = context.packageManager.getPackageInfo(
                    usageStats.packageName,
                    PackageManager.GET_ACTIVITIES
                )
                packageToAppNameMap[usageStats.packageName] = packageInfo.applicationInfo.loadLabel(
                    context.packageManager
                ).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the exception if necessary
            }
        }

        // Calculate WiFi usage time for each app and log the result
        for (usageStats in usageStatsList) {
            networkStats.getNextBucket(bucket)
            val wifiUsage = bucket.rxBytes + bucket.txBytes
            val packageName = usageStats.packageName
            val appName = packageToAppNameMap[packageName]
            val wifiUsageTimeInMillis = usageStats.totalTimeInForeground

            if (appName !== null) {
                val formattedWifiUsage = formatDataUsage(wifiUsage)
                logToConsole("$appName: 와이파이 사용량: $formattedWifiUsage")
            }
        }
    }
    private fun formatDataUsage(wifiUsage: Long): String {
        val kbLimit = 1024 // 1 KB in bytes
        val mbLimit = kbLimit * 1024 // 1 MB in bytes
        val gbLimit = mbLimit * 1024 // 1 GB in bytes

        return when {
            wifiUsage >= gbLimit -> {
                String.format("%.2f GB", wifiUsage.toDouble() / gbLimit)
            }
            wifiUsage >= mbLimit -> {
                String.format("%.2f MB", wifiUsage.toDouble() / mbLimit)
            }
            wifiUsage >= kbLimit -> {
                String.format("%.2f KB", wifiUsage.toDouble() / kbLimit)
            }
            else -> {
                "$wifiUsage bytes"
            }
        }
    }

    private fun logToConsole(message: String) {
        val textView = TextView(requireContext())

        textView.text = message

        textView.textSize = 30f

        textView.setTypeface(null, Typeface.BOLD)

        textView.id = 1

        textView.setTextColor(Color.BLACK)
        val linearLayout = binding.listLayout.getChildAt(0) as LinearLayout
        linearLayout.addView(textView)
    }
>>>>>>> cc0e503 (Initial commit)
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
<<<<<<< HEAD
=======

>>>>>>> cc0e503 (Initial commit)
}