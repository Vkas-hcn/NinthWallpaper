package com.best.ninth.app.ninthwallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.best.ninth.app.ninthwallpaper.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    val binding: ActivitySettingBinding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.txtPrivacyAgreement.setOnClickListener {
            NetActivity.startActivity(this)
        }
        binding.imgBackAway.setOnClickListener {
            finish()
        }
    }
}