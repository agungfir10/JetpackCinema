package com.agungfir.jetpackcinema.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.agungfir.jetpackcinema.R
import com.agungfir.jetpackcinema.ui.home.HomeActivity
import dagger.android.support.DaggerAppCompatActivity

class SplashActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 2000)
    }
}