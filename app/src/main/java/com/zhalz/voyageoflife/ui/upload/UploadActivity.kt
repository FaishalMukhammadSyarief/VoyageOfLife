package com.zhalz.voyageoflife.ui.upload

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityUploadBinding
import com.zhalz.voyageoflife.utils.Const.Image.AUTHORITY
import com.zhalz.voyageoflife.utils.Const.Image.REQUEST_CODE_CAMERA
import com.zhalz.voyageoflife.utils.ToastMaker.toast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UploadActivity : AppCompatActivity() {

    private val binding: ActivityUploadBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_upload) }
    private val viewModel: UploadViewModel by viewModels()

    private lateinit var imageFile: File

    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload)

        binding.activity = this
        binding.viewmodel = viewModel
    }

    private fun launchCamera() {
        imageFile = createImageFile()
        val photoURI = FileProvider.getUriForFile(this, AUTHORITY, imageFile)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        cameraLauncher.launch(cameraIntent)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) setImage(imageFile)
        }

    fun openCamera() =
        if (isPermissionGranted(CAMERA)) launchCamera()
        else requestPermission(CAMERA, REQUEST_CODE_CAMERA)

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_", ".jpg", storageDir)
    }

    private fun setImage(file: File) {
        imageFile = file
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        binding.ivImage.setImageBitmap(bitmap)
    }

    /** -- RUNTIME PERMISSION -- **/
    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(permission: String, requestCode: Int) =
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            toast(R.string.permission_not_granted)
            return
        }
        when (requestCode) {
            REQUEST_CODE_CAMERA -> openCamera()
        }
    }

}