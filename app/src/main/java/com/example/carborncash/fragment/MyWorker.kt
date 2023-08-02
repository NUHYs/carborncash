package com.example.carborncash.fragment

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    private val context = appContext

    override fun doWork(): Result {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 1000 * 60 * 60 // 1시간 전
        val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)

        val recentApps = mutableListOf<String>()
        while (usageEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            usageEvents.getNextEvent(event)
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                recentApps.add(event.packageName)
            }
        }
        return Result.success()
    }
}

