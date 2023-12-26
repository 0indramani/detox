package com.detox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.detox.databinding.ActivityMainBinding
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.view.*
import android.graphics.*
import android.os.Build;
import android.content.pm.ResolveInfo;
import android.widget.Toast
import android.widget.Button
import android.graphics.drawable.*
import androidx.recyclerview.widget.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import com.detox.adapter.*
import com.detox.dataclass.*
import android.os.Handler
import android.os.Looper
//import kotlinx.coroutines.flow.Flow;
import android.text.*

public class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val baseObj = BaseObj
    
    lateinit var adapter : AddAppAdapter
    private val binding: ActivityMainBinding
      get() = checkNotNull(_binding) { "Activity has been destroyed" }
      
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.texttt.setIs24HourView(true)
        binding.allAppss.isChecked = true
        setSupportActionBar(binding.toolbar)
        
        adapter = AddAppAdapter(this, getInstalledAppInfo()!!)
        binding.selectedApp.layoutManager = GridLayoutManager(this, 3)
        binding.selectedApp.adapter = adapter
        
        if (!baseObj.isRunning) {
            binding.startt.text = "Start"
        } else {
            binding.startt.text = "Stop"
        }
        binding.startt.setOnClickListener {
            baseObj.timesInSecond = (((binding.texttt.getHour() * 60 * 60) + (binding.texttt.getMinute() * 60 )) * 1000).toLong()// ((((binding.texttt.getCurrentHour() *  60 * 60) + binding.texttt.getCurrentMinute()) * 60) * 1000).toLong()
            if (!baseObj.isRunning) {
                baseObj.isRunning = true
                binding.startt.text = "Stop"
                baseObj.isPermanent = binding.allAppss.isChecked()
                startService(Intent( this@MainActivity, MainService::class.java ) );
            } else {
                binding.startt.text = "Start"
                baseObj.isRunning = false
                stopService( Intent( this@MainActivity, MainService::class.java ) ); 
            }
          //  binding.textt.text = Editable.Factory.getInstance().newEditable(baseObj.allData.toString())
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    
    fun getInstalledAppInfo() : MutableList<PacData>? {
        val packageManager = getPackageManager()
        var appInfo : List<ResolveInfo>? = null
        var pkgNames : MutableList<PacData> = ArrayList()
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            appInfo = this.packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong()))
        }else {
            appInfo = this.packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
            PackageManager.GET_META_DATA)
        }
        var c = 0
        for(info in appInfo) {
            pkgNames.add(PacData(info.activityInfo.packageName.toString()))
            c++
        }
        return pkgNames
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.help -> {
                startActivity(Intent(this, SplashActivity::class.java))
                return true
            } 
            else -> false
        }
    }
}
