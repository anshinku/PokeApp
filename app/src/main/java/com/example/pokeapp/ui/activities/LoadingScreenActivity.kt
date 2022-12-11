package com.example.pokeapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.pokeapp.databinding.ActivitySplashScreenBinding

class LoadingScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.myLooper()!!)

        handler.postDelayed(
            {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()

            },1000
        )

    }
}