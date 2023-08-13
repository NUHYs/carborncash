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

                val uid = bucket.uid
                val packageNames = getPackageNamesFromUid(context, uid)

                packageNames?.forEach { packageName ->
                    val appInfo = getAppInfo(context, packageName)
                    val appName = appInfo?.loadLabel(context.packageManager).toString()

                    val downloads = bucket.rxBytes
                    val uploads = bucket.txBytes
                    val totalUsageInKB = (downloads + uploads) / (1024 * 1024.0) // MB

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
        fun getDayUsage(context: Context): String {
            val c = Calendar.getInstance()
            c[Calendar.HOUR_OF_DAY] = 0
            c[Calendar.MINUTE] = 0
            c[Calendar.SECOND] = 0
            c[Calendar.MILLISECOND] = 0
            val startOfToday = c.timeInMillis

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            val end = calendar.timeInMillis

            return getUsage(context, startOfToday, end)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun getUsage(context: Context, start: Long, end: Long): String {
            var downloads = 0L
            var uploads = 0L
            val bucket = NetworkStats.Bucket()
            val networkStatsManager =
                context.getSystemService(AppCompatActivity.NETWORK_STATS_SERVICE) as NetworkStatsManager


            val stats = networkStatsManager.queryDetails(
                ConnectivityManager.TYPE_WIFI,
                null,
                start,
                end
            )
            while (stats.hasNextBucket()) {
                stats.getNextBucket(bucket)
                downloads += bucket.rxBytes
                uploads += bucket.txBytes
            }
            val mobilestats = networkStatsManager.queryDetails(
                ConnectivityManager.TYPE_MOBILE,
                null,
                start,
                end
            )
            while (mobilestats.hasNextBucket()) {
                mobilestats.getNextBucket(bucket)
                downloads += bucket.rxBytes
                uploads += bucket.txBytes
            }
            val resultInMB = (downloads + uploads) / (1024 * 1024.0)
            return if (resultInMB > 999) {
                String.format(
                    "%.2f",
                    resultInMB / 1000.0
                ) + " GB"
            } else {
                String.format(
                    "%.0f",
                    resultInMB
                ) + " MB"
            }
        }

    }
}