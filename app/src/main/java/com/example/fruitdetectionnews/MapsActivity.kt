package com.example.fruitdetectionnews

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        var permissionArrays = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        layoutPasar.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsActivity, PasarActivity::class.java)
            startActivity(intent)
        }

        layoutSupermarket.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsActivity, SupermarketActivity::class.java)
            startActivity(intent)
        }

        layoutSayur.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsActivity, VegetableActivity::class.java)
            startActivity(intent)
        }

        layoutBuah.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsActivity, FruitActivity::class.java)
            startActivity(intent)
        }

    }
}