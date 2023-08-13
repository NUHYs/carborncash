package com.example.carborncash.fragment

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DataProvider {

    companion object {
//        @RequiresApi(Build.VERSION_CODES.M)
//        fun getTotalUsage(context: Context): String {
//            val calendar = Calendar.getInstance()
//            calendar.add(Calendar.DATE, 1)
//            val end = calendar.timeInMillis
//            return getUsage(context, 0, end)
//        }


//        @RequiresApi(Build.VERSION_CODES.M)
//        fun getMonthUsage(context: Context): String {
//            val c2 = Calendar.getInstance()
//            c2.add(Calendar.DATE, -30)
//            val startOfMonth = c2.timeInMillis
//
//            val calendar = Calendar.getInstance()
//            calendar.add(Calendar.DATE, 1)
//            val end = calendar.timeInMillis
//
//            return getUsage(context, startOfMonth, end)
//        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun getUsageSummary(context: Context): Map<String, String> {
            val c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            val startOfToday = c.timeInMillis

            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 23
            calendar[Calendar.MINUTE] = 59
            calendar[Calendar.SECOND] = 59
            calendar[Calendar.MILLISECOND] = 999
            val endOfToday = calendar.timeInMillis

            val networkStatsManager = context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

            val appUsageMap = mutableMapOf<String, Double>()
            val bucket = NetworkStats.Bucket()


            val wifiStats = networkStatsManager.queryDetails(ConnectivityManager.TYPE_WIFI, "", startOfToday, endOfToday)
            val mobileStats = networkStatsManager.queryDetails(ConnectivityManager.TYPE_MOBILE, "", startOfToday, endOfToday)

            while (wifiStats.hasNextBucket() || mobileStats.hasNextBucket()) {
                if (wifiStats.hasNextBucket()) {
                    wifiStats.getNextBucket(bucket)
                } else {
                    mobileStats.getNextBucket(bucket)
                }
                Log.d("aaaaaa", "GGGGGGG $wifiStats")
                Log.d("aaaaaa", "HHHHHHHH $mobileStats")


                val uid = bucket.uid
                val packageNames = getPackageNamesFromUid(context, uid)
                Log.d("aaaaaa", "aaaa $packageNames")

                packageNames?.forEach { packageName ->
                    val appInfo = getAppInfo(context, packageName)
                    Log.d("aadaaaa", "$appInfo")
                    val appName = appInfo?.loadLabel(context.packageManager).toString()

                    val downloads = bucket.rxBytes
                    val uploads = bucket.txBytes
                    val totalUsageInKB = (downloads + uploads) / (1024 * 1024.0) // MB
                    Log.d("aaaaaa", "cccccc $appName $totalUsageInKB")

                    appUsageMap[appName] = appUsageMap.getOrDefault(appName, 0.0) + totalUsageInKB


                }
            }

            wifiStats.close()
            mobileStats.close()

            val formattedAppUsageMap = appUsageMap.mapValues { (appName, usageInMB) ->
                if (usageInMB > 999) {
                    String.format("%.2f GB", usageInMB / 1000.0)
                } else {
                    String.format("%.0f MB", usageInMB)
                }
            }

            return formattedAppUsageMap
        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun getAppInfo(context: Context, packageName: String): ApplicationInfo? {
            return try {
                context.packageManager.getApplicationInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
        }

        private fun getPackageNamesFromUid(context: Context, uid: Int): List<String>? {
            val pm = context.packageManager
            val packageNames = pm.getPackagesForUid(uid)
            return packageNames?.toList()
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun getUsage(context: Context, start: Long, end: Long): Map<String, String> {
            val bucket = NetworkStats.Bucket()
            val networkStatsManager =
                context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

            val pm = context.packageManager
            val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

            val appUsageMap = mutableMapOf<String, String>()
            for (packageInfo in packages) {
                val uid = packageInfo.uid
                val packageName = packageInfo.packageName

                val stats = networkStatsManager.queryDetails(
                    ConnectivityManager.TYPE_WIFI,
                    null,
                    start,
                    end
                )

                var downloads = 0L
                var uploads = 0L

                while (stats.hasNextBucket()) {
                    stats.getNextBucket(bucket)
                    if (bucket.uid == uid) {
                        downloads += bucket.rxBytes
                        uploads += bucket.txBytes
                    }
                }

                stats.close() // stats.close()를 루프 밖으로 이동

                val resultInMB = (downloads + uploads) / (1024 * 1024.0)
                appUsageMap[packageName] =
                    if (resultInMB > 999) {
                        String.format("%.2f", resultInMB / 1000.0) + " GB"
                    } else {
                        String.format("%.0f", resultInMB) + " MB"
                    }
            }
            return appUsageMap
        }
    }
}