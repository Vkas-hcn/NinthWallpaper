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