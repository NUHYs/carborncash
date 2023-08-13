import android.Manifest
import android.app.AppOpsManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.carborncash.R
import com.example.carborncash.fragment.DataProvider

class totalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val tvToday = view.findViewById<TextView>(R.id.tv_usage_today)
        val tvTotal = view.findViewById<TextView>(R.id.tv_usage_total)
        val tvMonth = view.findViewById<TextView>(R.id.tv_usage_month)

        tvToday.text = DataProvider.getUsageSummary(context).toString()
//        tvMonth.text = DataProvider.getMonthUsage(context)
//        tvTotal.text = DataProvider.getTotalUsage(context)

    }

    private fun grantPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            1
        )
    }

    private fun isAccessGranted(context: Context): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_PHONE_STATE
        )
        return try {
            val packageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
            val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode: Int = appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), applicationInfo.packageName
            )
            mode == AppOpsManager.MODE_ALLOWED && permission == PackageManager.PERMISSION_GRANTED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}