package com.example.fruitdetectionnews

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ramotion.circlemenu.CircleMenuView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fruitdetectionnews.R.layout.activity_main)

        val menu = findViewById<CircleMenuView>(com.example.fruitdetectionnews.R.id.circle_menu)
        menu.setEventListener(object : CircleMenuView.EventListener() {

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationEnd| index: $index")

                when (index){
                    0 -> {
                        val intent = Intent (this@MainActivity, ScanActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent (this@MainActivity, MapsActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent (this@MainActivity, NewsActivity::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent (this@MainActivity, HistoryActivity::class.java)
                        startActivity(intent)
                    }

                }
            }
        })

    }
}