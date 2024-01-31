package com.best.ninth.app.ninthwallpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.best.ninth.app.ninthwallpaper.ClockUtils.wallPaperPosition
import com.best.ninth.app.ninthwallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initMainAdapter()
        binding.imgSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    private fun initMainAdapter() {
        val adapter = MainAdapter(this, NinthUtils.allList)
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewMain.adapter = adapter
        adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                wallPaperPosition = position
                val intent = Intent(this@MainActivity, FinishActivity::class.java)
                startActivity(intent)
            }
        })
    }
}