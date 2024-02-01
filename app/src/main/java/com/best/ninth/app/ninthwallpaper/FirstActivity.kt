package com.best.ninth.app.ninthwallpaper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import com.best.ninth.app.ninthwallpaper.databinding.ActivityFirstBinding
import com.best.ninth.app.ninthwallpaper.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class FirstActivity : AppCompatActivity() {
    val binding:ActivityFirstBinding by lazy { ActivityFirstBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        startCountdown()
        ClockUtils.blackUrl.getBlackList(this)
    }
    private fun startCountdown() {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 2000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            binding.progressBarFirst.progress = progress
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@FirstActivity, MainActivity::class.java))
                finish()
            }
        })
        animator.start()
    }

    private fun String.getBlackList(context: Context) {
        if (ClockUtils.ninth_black_key.isNotEmpty()) {
            return
        }
        lifecycleScope.launch(Dispatchers.IO) {
            getMapData(this@getBlackList,ClockUtils.cloakMapData(context), onNext = {
                Log.e("TAG", "The blacklist request is successful：$it")
                ClockUtils.ninth_black_key = it
            }, onError = {
                ClockUtils.blackUrl.retry(it,context)
            })
        }
    }

    private fun String.retry(it: String,context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(10002)
            Log.e("TAG", "The blacklist request failed：$it")
            this@retry.getBlackList(context)
        }
    }

    private fun getMapData(url: String, map: Map<String, Any>, onNext: (response: String) -> Unit, onError: (error: String) -> Unit) {
        val queryParameters = StringBuilder()
        for ((key, value) in map) {
            if (queryParameters.isNotEmpty()) {
                queryParameters.append("&")
            }
            queryParameters.append(URLEncoder.encode(key, "UTF-8"))
            queryParameters.append("=")
            queryParameters.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }

        val urlString = if (url.contains("?")) {
            "$url&$queryParameters"
        } else {
            "$url?$queryParameters"
        }

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 15000 // 设置连接超时时间

        try {
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                inputStream.close()
                onNext(response.toString())
            } else {
                onError("HTTP error: $responseCode")
            }
        } catch (e: Exception) {
            onError("Network error: ${e.message}")
        } finally {
            connection.disconnect()
        }
    }
}