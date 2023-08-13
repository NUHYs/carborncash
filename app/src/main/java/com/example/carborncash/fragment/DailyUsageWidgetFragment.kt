package com.example.carborncash.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carborncash.R
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DailyUsageWidgetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyUsageWidgetFragment : AppWidgetProvider() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent!!.action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) || intent.action
                .equals(Intent.ACTION_SCREEN_ON)
        ) {
            val extras = intent.extras
            if (extras != null) {
                Toast.makeText(context, "Updating...", Toast.LENGTH_SHORT).show()
                if (context != null) {
                    updateAppWidget(
                        context,
                        AppWidgetManager.getInstance(context),
                        extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_IDS)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val remoteV = RemoteViews(context.packageName, R.layout.fragment_daily_usage_widget)
        val intentSync = Intent(context, DailyUsageWidgetFragment::class.java)
        intentSync.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, arrayOf(appWidgetId))
        intentSync.action =
            AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val pendingSync = PendingIntent.getBroadcast(
            context,
            0,
            intentSync,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteV.setOnClickPendingIntent(R.id.appwidget_image, pendingSync)
        remoteV.setTextViewText(
            R.id.appwidget_text,
            DataProvider.getUsageSummary(context).toString()
        )
        appWidgetManager.updateAppWidget(
            ComponentName(context, DailyUsageWidgetFragment::class.java),
            remoteV
        )
    }
}