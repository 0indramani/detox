package com.detox

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.*
import android.graphics.PixelFormat
import android.os.Handler
import android.os.IBinder
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.app.usage.*
import kotlin.properties.Delegates;
import android.os.Process
import pl.droidsonroids.gif.GifImageView
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.*;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.content.pm.*
import android.app.ActivityManager;
import com.detox.dataclass.*

class MainService : Service() {

    lateinit var windowManager: WindowManager
    lateinit var floatingView: View
    lateinit var floatingView2: View
    lateinit var handler: Handler
    lateinit var textView: TextView
    lateinit var meme : GifImageView
    lateinit var memeText : TextView
    lateinit var params : WindowManager.LayoutParams 
    var allData : MutableList<String> = BaseObj.allData
    private var lastPackageName: String? = null 
    
    private var notificationIcon : Bitmap? = null
    private var notification : Notification? = null
    var notificationManager : NotificationManager? = null
    private val notificationId : Int = 143

    override fun onCreate() {
        super.onCreate()
        generateNotification()
        
        handler = Handler()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT, // Adjust height as needed
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP
        params.x = 0
        params.y = 100

        floatingView = LayoutInflater.from(this).inflate(R.layout.service_main, null)
        floatingView2 = LayoutInflater.from(this).inflate(R.layout.service_main, null)
        
        if(BaseObj.isPermanent) {
            windowManager.addView(floatingView, params)
            object : CountDownTimer(BaseObj.timesInSecond, 1000) {
            override fun onTick(millisUntilFinished: Long) { 
                floatingView.findViewById<TextView>(R.id.count).setText((millisUntilFinished /1000).toString() + " seconds")//setText("seconds remaining: " + millisUntilFinished / 1000) 
            }
            override fun onFinish() {
                floatingView.findViewById<TextView>(R.id.count).setText("done!") 
                closeFloatingWindow()
                BaseObj.isRunning = false
            } 
        }.start() 
        }else {
            //generateNotification()
            object : CountDownTimer(BaseObj.timesInSecond, 1000) {
            override fun onTick(millisUntilFinished: Long) { 
               // cnTime = (millisUntilFinished /1000).toLong()
                floatingView2.findViewById<TextView>(R.id.count).setText((millisUntilFinished /1000).toString() + " seconds")
            }
            override fun onFinish() {
                floatingView2.findViewById<TextView>(R.id.count).setText("done!") 
                closeFloatingWindow2()
                BaseObj.isRunning = false
            } 
        }.start() 
        handler.post(checkPackageNameRunnable)
        }
        
    }

    private val checkPackageNameRunnable = object : Runnable {
        override fun run() {
            val currentPackageName = getCurrentPackageName()
            if (currentPackageName != lastPackageName) {
                if (allData.contains(currentPackageName)) {
                
                    floatingView2.findViewById<GifImageView>(R.id.meme).visibility = View.VISIBLE
                    floatingView2.findViewById<TextView>(R.id.memeText).visibility = View.VISIBLE
                    
                    windowManager.addView(floatingView2, params)
                    
                } else if (!allData.contains(currentPackageName)) {
                
                    if(floatingView2.findViewById<GifImageView>(R.id.meme).visibility == View.VISIBLE) {
                        floatingView2.findViewById<GifImageView>(R.id.meme).visibility = View.INVISIBLE
                        windowManager.removeView(floatingView2)
                    } 
                    
                }
                lastPackageName = currentPackageName
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
       // closeFloatingWindow()
        handler.removeCallbacks(checkPackageNameRunnable)
    }

    private fun closeFloatingWindow() {
        if (windowManager != null && floatingView != null) {
            windowManager.removeView(floatingView)
            stopSelf() // Stop the service
        }
    }
    private fun closeFloatingWindow2() {
        if (windowManager != null && floatingView2 != null) {
            windowManager.removeView(floatingView2)
            stopSelf() // Stop the service
        }
    }
    

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun getCurrentPackageName(): String? {
        val usageStatsManager = applicationContext.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        try {
            val currentTime = System.currentTimeMillis()
            val queryEvents = usageStatsManager.queryEvents(currentTime - 60000, currentTime) // Query events within the last minute
            var packageName: String? = null
            while (queryEvents.hasNextEvent()) {
                val event = UsageEvents.Event()
                queryEvents.getNextEvent(event)
                if (event.eventType == 1) {
                    packageName = event.packageName
                }
            }
            return packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    private fun generateNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        
            val mainIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)
            
            notificationIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)
            if (notificationManager == null) {
                notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(notificationManager != null)
                notificationManager?.createNotificationChannelGroup (
                    NotificationChannelGroup("io","ioo")
                )
                var notificationChannel = NotificationChannel("service_channel", "servicess", NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                notificationManager?.createNotificationChannel(notificationChannel)
            }
           
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle("Timer")
                .setTicker("Repairing .so files")
                .setContentText("hsjjss")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                
            if (notificationIcon != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(notificationIcon!!, 128, 128, false))
            }
            builder.color = resources.getColor(R.color.purple_200)
            notification = builder.build()
            startForeground(notificationId, notification)
        }
    }

}