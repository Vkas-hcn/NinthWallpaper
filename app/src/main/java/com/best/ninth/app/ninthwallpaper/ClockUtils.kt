package com.best.ninth.app.ninthwallpaper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.util.UUID

object ClockUtils {
    const val agreementUrl = "https://www.baidu.com"
    const val blackUrl = "https://marina.wallpaperscenic.com/quote/jew/appendix"
    private val sharedPreferences by lazy {
        App.instance.getSharedPreferences(
            "NinthWallpaper",
            Context.MODE_PRIVATE
        )
    }
    var ninth_id = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("ninth_id", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("ninth_id", "").toString()

    var ninth_black_key = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("ninth_black_value", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("ninth_black_value", "").toString()

    var wallPaperPosition = 0
        set(value) {
            sharedPreferences.edit().run {
                putInt("wallPaperPosition", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getInt("wallPaperPosition", 0)
    var black_uuid = ""
        set(value) {
            sharedPreferences.edit().run {
                putString("black_uuid", value)
                commit()
            }
            field = value
        }
        get() = sharedPreferences.getString("black_uuid", "").toString()

    fun getUUID(): String {
        return UUID.randomUUID().toString()
    }
    fun cloakMapData(context: Context): Map<String, Any> {
        return mapOf<String, Any>(
            //distinct_id
            "twaddle" to (ninth_id),
            //client_ts
            "crevice" to (System.currentTimeMillis()),
            //device_model
            "vector" to Build.MODEL,
            //bundle_id
            "spill" to ("com.wallpaper.scenic.views.nature"),
            //os_version
            "sprocket" to Build.VERSION.RELEASE,
            //gaid
            "section" to "",
            //android_id
            "eng" to context.getAppId(),
            //os
            "compact" to "kola",
            //app_version
            "vought" to context.getAppVersion(),
            //network_type
            "leopard" to "",
        )
    }

    private fun Context.getAppVersion(): String {
        try {
            val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)

            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Version information not available"
    }

    private fun Context.getAppId(): String {
        return Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}