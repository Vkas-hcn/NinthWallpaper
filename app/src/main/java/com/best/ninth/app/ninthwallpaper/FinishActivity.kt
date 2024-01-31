package com.best.ninth.app.ninthwallpaper

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.best.ninth.app.ninthwallpaper.databinding.ActivityFinishBinding
import kotlinx.coroutines.Dispatchers

class FinishActivity : AppCompatActivity() {
    val binding: ActivityFinishBinding by lazy { ActivityFinishBinding.inflate(layoutInflater) }
    private val REQUEST_PERMISSION_CODE = 123

    private var drawable: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        drawable = NinthUtils.allList[ClockUtils.wallPaperPosition]
        binding.imageViewFinish.setImageResource(drawable)
    }

    private fun initView() {
        binding.tvApply.setOnClickListener {
            binding.llBottom.visibility = View.GONE
            binding.llDialog.visibility = View.VISIBLE
        }
        binding.tvCancel.setOnClickListener {
            binding.llBottom.visibility = View.VISIBLE
            binding.llDialog.visibility = View.GONE
        }
        binding.imageViewBackAway.setOnClickListener {
            finish()
        }
        binding.tvHS.setOnClickListener {
            NinthUtils.setHomeWallPaper(this, drawable)
        }
        binding.tvLS.setOnClickListener {
            NinthUtils.setLockWallPaper(this, drawable)
        }
        binding.tvB.setOnClickListener {
            NinthUtils.setBothWallPaper(this, drawable)
        }
        binding.tvDownload.setOnClickListener {
            if (hasWritePermission() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                downloadAndSaveImage()
            } else {
                requestWritePermission()
            }
        }
    }

    private fun hasWritePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestWritePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadAndSaveImage()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setMessage("To save the image to the gallery, go to the settings page to grant permission.")
            .setPositiveButton("Go to Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    this,
                    "There is no storage permission to save the picture.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun downloadAndSaveImage() {
        GlobalScope.launch(Dispatchers.IO) {
            val imageDrawable = ContextCompat.getDrawable(this@FinishActivity, drawable)
            if (imageDrawable is BitmapDrawable) {
                val bitmap = imageDrawable.bitmap
                val savedUri = saveImageToGallery(bitmap, "wallpaper_${System.currentTimeMillis()}", "My Image Description")
                if (savedUri != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@FinishActivity,
                            "The picture has been saved to the gallery.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@FinishActivity,
                            "Failed to save picture",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap, title: String, description: String): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, title)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DESCRIPTION, description)
        }

        val contentResolver: ContentResolver = contentResolver
        val imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        imageUri?.let {
            val outputStream: OutputStream? = contentResolver.openOutputStream(it)
            outputStream?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                return imageUri
            }
        }
        return null
    }
}