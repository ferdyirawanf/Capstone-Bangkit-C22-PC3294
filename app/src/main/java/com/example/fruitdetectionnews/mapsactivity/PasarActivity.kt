package com.example.fruitdetectionnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.ProgressDialog
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruitdetectionnews.R
import com.example.fruitdetectionnews.adapter.MainAdapter
import com.example.fruitdetectionnews.data.model.ModelResults
import com.example.fruitdetectionnews.viewmodel.ListResultViewModel
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*

class PasarActivity : AppCompatActivity() {
    lateinit var simpleLocation: SimpleLocation
    lateinit var strLokasi: String
    lateinit var progressDialog: ProgressDialog
    lateinit var mainAdapter: MainAdapter
    lateinit var listResultViewModel: ListResultViewModel
    var strLatitude = 0.0
    var strLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("sedang menampilkan lokasi")

        //set library location
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get location
        strLatitude = simpleLocation.latitude
        strLongitude = simpleLocation.longitude

        //set location lat long
        strLokasi = "$strLatitude,$strLongitude"

        //set title
        tvTitle.setText("Pasar Terdekat")

        //set data adapter
        mainAdapter = MainAdapter(this)
        rvListResult.setLayoutManager(LinearLayoutManager(this))
        rvListResult.setAdapter(mainAdapter)
        rvListResult.setHasFixedSize(true)

        //viewmodel
        listResultViewModel = ViewModelProvider(this, NewInstanceFactory()).get(ListResultViewModel::class.java)
        listResultViewModel.setMarketLocation(strLokasi)
        progressDialog.show()
        listResultViewModel.getMarketLocation().observe(this, { modelResults: ArrayList<ModelResults>? ->
            if (modelResults!!.size != 0) {
                mainAdapter.setResultAdapter(modelResults)
                progressDialog.dismiss()
            } else {
                Toast.makeText(this, "Oops, lokasi Pasar tidak ditemukan!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}