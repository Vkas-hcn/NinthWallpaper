package com.best.ninth.app.ninthwallpaper

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.view.View
import com.best.ninth.app.ninthwallpaper.databinding.ActivityFinishBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NinthUtils {

    val allList = listOf(
        R.drawable.icon_1,
        R.drawable.icon_2,
        R.drawable.icon_3,
        R.drawable.icon_4,
        R.drawable.icon_5,
        R.drawable.icon_6,
        R.drawable.icon_7,
        R.drawable.icon_8,
        R.drawable.icon_9,
        R.drawable.icon_10,
        R.drawable.icon_11,
        R.drawable.icon_12,
        R.drawable.icon_13,
        R.drawable.icon_14,
        R.drawable.icon_15,
        R.drawable.icon_16,
        R.drawable.icon_17,
        R.drawable.icon_18,
        R.drawable.icon_19,
        R.drawable.icon_20,
        R.drawable.icon_21,
        R.drawable.icon_22,
        R.drawable.icon_23,
        R.drawable.icon_24,
        R.drawable.icon_25,
        R.drawable.icon_26,
        R.drawable.icon_27,
        R.drawable.icon_28,
        R.drawable.icon_29,
        R.drawable.icon_30,
        R.drawable.icon_31,
        R.drawable.icon_32,
        R.drawable.icon_33,
        R.drawable.icon_34,
        R.drawable.icon_35,
        R.drawable.icon_36,
        R.drawable.icon_37,
        R.drawable.icon_38,
        R.drawable.icon_39,
        R.drawable.icon_40,
        R.drawable.icon_41,
        R.drawable.icon_42,
        R.drawable.icon_43,
        R.drawable.icon_44,
        R.drawable.icon_45,
        R.drawable.icon_46,
        R.drawable.icon_47,
        R.drawable.icon_48,
        R.drawable.icon_49,
        R.drawable.icon_50,
        R.drawable.icon_51,
        R.drawable.icon_52,
        R.drawable.icon_53,
        R.drawable.icon_80,
        R.drawable.icon_81,
        R.drawable.icon_82,
        R.drawable.icon_83,
        R.drawable.icon_84,
        R.drawable.icon_88,
        R.drawable.icon_89,
        R.drawable.icon_90,
        R.drawable.icon_91,
        R.drawable.icon_93,
        R.drawable.icon_94,
        R.drawable.icon_95,
        R.drawable.icon_96,
        R.drawable.icon_98,
        R.drawable.icon_99,
    )

    fun setHomeWallPaper(activity: FinishActivity, drawable: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val binding = activity.binding
            binding.progressBarFinish.visibility = View.VISIBLE
            withContext(Dispatchers.IO) {
                val wallpaperManager = WallpaperManager.getInstance(activity)
                val bitmap = BitmapFactory.decodeResource(activity.resources, drawable)
                wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_SYSTEM)
            }
            setFinishFun(binding)
        }
    }

    fun setLockWallPaper(activity: FinishActivity, drawable: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val binding = activity.binding
            binding.progressBarFinish.visibility = View.VISIBLE
            withContext(Dispatchers.IO) {
                val wallpaperManager = WallpaperManager.getInstance(activity)
                val bitmap = BitmapFactory.decodeResource(activity.resources, drawable)
                wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK)
            }
            setFinishFun(binding)
        }
    }
    private fun setFinishFun(binding: ActivityFinishBinding){
        binding.progressBarFinish.visibility = View.GONE
        binding.llDialog.visibility = View.GONE
        binding.llBottom.visibility = View.VISIBLE
    }

    fun setBothWallPaper(activity: FinishActivity, drawable: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val binding = activity.binding
            binding.progressBarFinish.visibility = View.VISIBLE
            withContext(Dispatchers.IO) {
                val wallpaperManager = WallpaperManager.getInstance(activity)
                val bitmap = BitmapFactory.decodeResource(activity.resources, drawable)
                wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
            }
            setFinishFun(binding)
        }
    }
}