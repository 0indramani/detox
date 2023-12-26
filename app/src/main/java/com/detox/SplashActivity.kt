package com.detox;

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.detox.databinding.ActivitySplashBinding
import android.Manifest

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    
    private val binding: ActivitySplashBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.two.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        
        binding.one.setOnClickListener {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}