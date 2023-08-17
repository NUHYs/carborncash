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



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    companion object {
        private const val REQUEST_USAGE_ACCESS = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppRankBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        view.findViewById<View>(R.id.btnGetDataUsage).setOnClickListener {
            binding.btnGetDataUsage.visibility = View.GONE
            val textView = TextView(requireContext())
            val textView2 = TextView(requireContext())
            val context = requireContext()
            textView2.text = DataProvider.getDayUsage(context)

            textView2.textSize = 30f

            textView2.setTypeface(null, Typeface.BOLD)

            textView2.id = 2

            textView.setTextColor(Color.BLACK)
            val linearLayout = binding.listLayout.getChildAt(0) as LinearLayout
            linearLayout.addView(textView2)

            textView.text = DataProvider.getUsageSummary(context).toString()

            textView.textSize = 30f

            textView.setTypeface(null, Typeface.BOLD)

            textView.id = 1

            textView.setTextColor(Color.BLACK)
            linearLayout.addView(textView)
//            if (!checkUsageStatsPermission()) {
//                requestUsageStatsPermission()
//            } else {
//                getDataUsageFromOtherApp()
//            }
        }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOps = context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
        val mode = context?.packageName?.let {
            appOps?.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
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
//        getYesterdayWiFiUsage()

        // 어제 와이파이 사용량 로그로 출력
        getTodayWiFiUsage()
    }



    private fun getTodayWiFiUsage() {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis

        val networkStatsManager =
            requireContext().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val bucket = NetworkStats.Bucket()
        val networkStats = networkStatsManager.querySummary(
            ConnectivityManager.TYPE_WIFI, "", startTime, endTime
        )

        val usageStatsManager = requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        val packageToAppNameMap = mutableMapOf<String, String>()

        for (usageStats in usageStatsList) {
            try {
                val packageInfo = requireContext().packageManager.getPackageInfo(
                    usageStats.packageName,
                    PackageManager.GET_ACTIVITIES
                )
                packageToAppNameMap[usageStats.packageName] = packageInfo.applicationInfo.loadLabel(
                    requireContext().packageManager
                ).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the exception if necessary
            }

            networkStats.getNextBucket(bucket)
            val wifiUsage = bucket.rxBytes + bucket.txBytes
            val packageName = usageStats.packageName
            val appName = packageToAppNameMap[packageName]
            val wifiUsageTimeInMillis = usageStats.totalTimeInForeground

            if (appName != null) {
                val formattedWifiUsage = formatDataUsage(wifiUsage)
                logToConsole("$appName: 오늘 와이파이 사용량: $formattedWifiUsage")
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

    private fun getYesterdayWiFiUsage() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1) // 어제로 설정
        val endTime = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis

        val networkStatsManager =
            requireContext().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

        val bucket = NetworkStats.Bucket()
        val networkStats = networkStatsManager.querySummary(
            ConnectivityManager.TYPE_WIFI, "", startTime, endTime
        )

        var totalWiFiUsage = 0L

        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket)
            totalWiFiUsage += bucket.rxBytes + bucket.txBytes
        }

        val formattedWiFiUsage = formatDataUsage(totalWiFiUsage)
        logToConsole("어제 와이파이 사용량: $formattedWiFiUsage")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}