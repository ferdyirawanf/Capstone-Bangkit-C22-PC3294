package com.example.fruitdetectionnews

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fruitdetectionnews.databinding.FragmentMapsBinding


class MapsFragment : Fragment() {

    var permissionArrays = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bind = FragmentMapsBinding.inflate(layoutInflater)

        bind.layoutPasar.setOnClickListener {
            val intent = Intent (this@MapsFragment.requireContext(), PasarActivity::class.java)
            startActivity(intent)
        }

        bind.layoutSupermarket.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsFragment.requireContext(), SupermarketActivity::class.java)
            startActivity(intent)
        }

        bind.layoutSayur.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsFragment.requireContext(), VegetableActivity::class.java)
            startActivity(intent)
        }

        bind.layoutBuah.setOnClickListener { view: View? ->
            val intent = Intent (this@MapsFragment.requireContext(), FruitActivity::class.java)
            startActivity(intent)
        }
        return bind.root

    }
}