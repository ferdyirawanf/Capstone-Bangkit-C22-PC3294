package com.example.fruitdetectionnews

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fruitdetectionnews.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bind = FragmentScanBinding.inflate(layoutInflater)
        bind.btnMulai.setOnClickListener {
            val intent = Intent (this@ScanFragment.requireContext(), ScanActivity::class.java)
            startActivity(intent)
        }
        return bind.root
    }
}