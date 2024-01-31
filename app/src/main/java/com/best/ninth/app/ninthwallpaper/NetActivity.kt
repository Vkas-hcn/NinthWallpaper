package com.best.ninth.app.ninthwallpaper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.best.ninth.app.ninthwallpaper.databinding.ActivityNetBinding

class NetActivity : AppCompatActivity() {
    val binding: ActivityNetBinding by lazy { ActivityNetBinding.inflate(layoutInflater) }
    companion object {
        const val TAG = "NetActivity"
        fun startActivity(activity: AppCompatActivity) {
            val intent = Intent(activity, NetActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imgBackAway.setOnClickListener {
            finish()
        }
        initWebView()
    }
    private fun initWebView() {
        binding.webViewPrivacyAgreement.loadUrl("https://www.baidu.com")
        binding.webViewPrivacyAgreement.webViewClient = object : android.webkit.WebViewClient() {
            override fun shouldOverrideUrlLoading(view: android.webkit.WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
    }

}