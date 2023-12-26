package com.detox.dataclass;

import com.detox.dataclass.PacData
import android.view.WindowManager;


object BaseObj {

    var isPermanent : Boolean = false
    var isRunning : Boolean = false
    var selectedApps : MutableList<PacData> = mutableListOf()
    var allData : MutableList<String> = mutableListOf()
    var xSize : Int = 100 //WindowManager.LayoutParams.MATCH_PARENT
    var ySize : Int = 100 
    var timesInSecond : Long = 0
}