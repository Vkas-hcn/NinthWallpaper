package com.best.ninth.app.ninthwallpaper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.util.UUID

object ClockUtils {
    const val agreementUrl = "https://www.baidu.com"
    const val blackUrl = "https://helmet.scapewallpaperengine.com/dense/falmouth"
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
            "basalt" to (ninth_id),
            //client_ts
            "onyx" to (System.currentTimeMillis()),
            //device_model
            "glissade" to Build.MODEL,
            //bundle_id
            "swelt" to ("com.scapewallpaper.engine"),
            //os_version
            "bathrobe" to Build.VERSION.RELEASE,
            //gaid
            "fairfax" to "",
            //android_id
            "flash" to context.getAppId(),
            //os
            "j" to "cardioid",
            //app_version
            "piggy" to context.getAppVersion(),
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