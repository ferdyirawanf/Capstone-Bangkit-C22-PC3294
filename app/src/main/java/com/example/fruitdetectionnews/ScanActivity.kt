package com.example.fruitdetectionnews

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BitmapCompat.createScaledBitmap
import com.example.fruitdetectionnews.ml.Model
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_scan.*
import okio.IOException
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ScanActivity : AppCompatActivity() {
    var REQUEST_CAMERA = 100
    var REQUEST_PICK_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if (isAlreadyHavePermission()) {
            requestForSpecificPermission()
        }

        var tvHasilKesegaran : TextView = findViewById(R.id.tvHasilKesegaran)
        var tvHasilAkurasi : TextView = findViewById(R.id.tvHasilAkurasi)

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
                            Toast.makeText(this@ScanActivity, "Gagal membuka kamera!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun predictImage(bitmapImage: Bitmap) {
        val model = Model.newInstance(applicationContext)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 100 * 100 * 3)
        byteBuffer.order(ByteOrder.nativeOrder());
        val intValues = IntArray(100 * 100)
        bitmapImage.getPixels(intValues, 0, bitmapImage.width, 0, 0, bitmapImage.width, bitmapImage.height)
        var pixel = 0
        for (i in 0 until 100) {
            for (j in 0 until 100) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("Fresh", "Rotten")
        Log.i("Result", confidences[0].toString())
        //Log.i("Result", confidences[1].toString())
        Log.i("Result", confidences.indices.toString())

        tvHasilKesegaran.setText(classes[maxPos])
        tvHasilAkurasi.setText((confidences[maxPos]*100).toString() + " %")

        // Releases model resources if no longer used.
        model.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            var image: Bitmap = data?.extras?.get("data") as Bitmap
            imagePreview.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, 100, 100, false);
            predictImage(image)
        } else if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            val dat: Uri? = data?.getData()
            var image: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dat)
            imagePreview.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, 100, 100, false);
            predictImage(image)
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