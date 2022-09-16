package com.example.battleconlifetracker.layouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, DynamicGameScreen::class.java)
        val extras = Bundle()
        intent.putExtras(extras)
        startActivity(intent)
        finish()
    }
}