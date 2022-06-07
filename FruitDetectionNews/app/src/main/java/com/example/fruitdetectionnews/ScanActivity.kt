package com.example.fruitdetectionnews

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_scan.*
import okio.IOException
import java.util.*
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.fragment_maps.*


class ScanActivity : AppCompatActivity() {
    var REQUEST_CAMERA = 100
    var REQUEST_PICK_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if (isAlreadyHavePermission()) {
            requestForSpecificPermission()
        }

        btnScan.setOnClickListener {
            showOptionDialog()
        }
    }

    private fun showOptionDialog() {
        val optionItems = arrayOf("Potret gambar dari kamera",
            "Pilih gambar dari galeri")
        val optionDialog = AlertDialog.Builder(this)
            .setTitle("Ambil Gambar Dari")
            .setItems(optionItems) {dialog, which ->
                when (which) {
                    0 -> fromCamera()
                    1 -> fromGallery()
                }
            }
        optionDialog.show()
    }

    private fun fromGallery() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun fromCamera() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        try {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, REQUEST_CAMERA)
                        } catch (ex: IOException) {
                            Toast.makeText(this@ScanActivity, "Gagal saat membuka kamera!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun predictImage(bitmapImage: Bitmap) {
        // TODO
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            var image: Bitmap = data?.extras?.get("data") as Bitmap
            imagePreview.setImageBitmap(image)
            predictImage(image)

            // if result fresh then change in text view ...
        } else if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            // TODO
        }
    }

    private fun isAlreadyHavePermission(): Boolean {
        val result: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
    }
}