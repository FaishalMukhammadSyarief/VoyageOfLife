package com.zhalz.voyageoflife.ui.upload

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.databinding.ActivityUploadBinding
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Const.Image.AUTHORITY
import com.zhalz.voyageoflife.utils.Const.Image.REQUEST_CODE_CAMERA
import com.zhalz.voyageoflife.utils.FileHelper.createTempFile
import com.zhalz.voyageoflife.utils.FileHelper.getBitmap
import com.zhalz.voyageoflife.utils.FileHelper.reduceFileImage
import com.zhalz.voyageoflife.utils.FileHelper.uriToFile
import com.zhalz.voyageoflife.utils.ToastMaker.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class UploadActivity : AppCompatActivity() {

    private val binding: ActivityUploadBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_upload) }
    private val viewModel: UploadViewModel by viewModels()
    private val imageFile: File by lazy { createTempFile() }

    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload)

        binding.activity = this
        binding.viewmodel = viewModel

        observeUpload()
    }

    fun uploadStory() {
        if (imageFile.getBitmap() == null) toast(R.string.please_select_an_image)
        else viewModel.uploadStory(description, imageFile.reduceFileImage())
    }

    private fun observeUpload() = lifecycleScope.launch {
        viewModel.uploadResponse.collect{
            when (it) {
                is ApiResult.Success -> {
                    binding.isLoading = false
                    toast(it.data?.message)
                    finish()
                }
                is ApiResult.Error -> {
                    binding.isLoading = false
                    toast(it.message)
                }
                is ApiResult.Loading -> binding.isLoading = true
            }
        }
    }

    /** -- GALLERY -- **/
    fun openGallery() {
        val galleryIntent = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivImage.setImageURI(uri)
                uriToFile(uri, imageFile)
            }
        }

    /** -- CAMERA -- **/
    fun openCamera() =
        if (isPermissionGranted(CAMERA)) launchCamera()
        else requestPermission(CAMERA, REQUEST_CODE_CAMERA)

    private fun launchCamera() {
        val photoURI = FileProvider.getUriForFile(this, AUTHORITY, imageFile)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        cameraLauncher.launch(cameraIntent)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) binding.ivImage.setImageBitmap(imageFile.getBitmap())
        }

    /** -- RUNTIME PERMISSION -- **/
    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(permission: String, requestCode: Int) =
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!grantResults.all { it == PackageManager.PERMISSION_GRANTED }) toast(R.string.permission_not_granted)
        else if (requestCode == REQUEST_CODE_CAMERA) openCamera()
    }

}
