
package com.detox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.detox.databinding.ActivityMainBinding

public class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    
    private val binding: ActivityMainBinding
      get() = checkNotNull(_binding) { "Activity has been destroyed" }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and get instance of binding
        _binding = ActivityMainBinding.inflate(layoutInflater)

        // set content view to binding's root
        setContentView(binding.root)
        Toast.makeText(this, "Done finally", Toast.LENGTH_LONG).show()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
