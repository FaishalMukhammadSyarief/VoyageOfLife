package com.zhalz.voyageoflife.ui.upload

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.data.remote.response.UploadResponse
import com.zhalz.voyageoflife.databinding.ActivityUploadBinding
import com.zhalz.voyageoflife.ui.home.HomeActivity
import com.zhalz.voyageoflife.utils.ActivityOpener.openActivity
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.Const.Image.AUTHORITY
import com.zhalz.voyageoflife.utils.Dialog.showDialog
import com.zhalz.voyageoflife.utils.FileHelper.createTempFile
import com.zhalz.voyageoflife.utils.FileHelper.getBitmap
import com.zhalz.voyageoflife.utils.FileHelper.reduceFileImage
import com.zhalz.voyageoflife.utils.FileHelper.uriToFile
import com.zhalz.voyageoflife.utils.ToastMaker.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadActivity : AppCompatActivity() {

    private val binding: ActivityUploadBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_upload) }
    private val viewModel by viewModels<UploadViewModel>()

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private var location: Location? = null

    private val imageFile by lazy { createTempFile() }

    var description = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload)

        binding.activity = this
        binding.viewmodel = viewModel

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_location)
                showDialog(
                    getString(R.string.location),
                    getString(R.string.msg_location),
                    { getLastLocation(); it.setIcon(R.drawable.ic_location_check) },
                    { location = null; it.setIcon(R.drawable.ic_location)}
                )
            true
        }
    }

    fun uploadStory() = lifecycleScope.launch {
        if (imageFile.getBitmap() == null) toast(R.string.please_select_an_image)
        else viewModel.uploadStory(imageFile.reduceFileImage(), description, location)
            .collect { processUpdateResult(it) }
    }

    private fun processUpdateResult(result: ApiResult<UploadResponse>) =
        when (result) {
            is ApiResult.Success -> {
                binding.isLoading = false
                toast(result.data?.message)
                openActivity<HomeActivity>(finishAll = true)
            }

            is ApiResult.Error -> {
                binding.isLoading = false
                toast(result.message)
            }

            is ApiResult.Loading -> binding.isLoading = true
        }

    private fun getLastLocation() {
        if (!checkPermission(ACCESS_COARSE_LOCATION) || !checkPermission(ACCESS_FINE_LOCATION)) return
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it == null) toast(R.string.error_location)
            location = it
        }
    }

    /** -- GALLERY -- **/
    fun openGallery() {
        val galleryIntent = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) return@registerForActivityResult
            binding.ivImage.setImageURI(uri)
            uriToFile(uri, imageFile)
        }

    /** -- CAMERA -- **/
    fun openCamera() {
        if (!checkPermission(CAMERA)) return
        val photoURI = FileProvider.getUriForFile(this, AUTHORITY, imageFile)
        cameraLauncher.launch(photoURI)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) binding.ivImage.setImageBitmap(imageFile.getBitmap())
        }

    /** -- RUNTIME PERMISSION -- **/
    private fun checkPermission(permission: String): Boolean =
        if (ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED) true
        else {
            requestPermissionLauncher.launch(arrayOf(permission))
            false
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions[CAMERA] == true -> openCamera()
                permissions[ACCESS_FINE_LOCATION] == true ||
                permissions[ACCESS_COARSE_LOCATION] == true -> getLastLocation()
                else -> toast(R.string.permission_not_granted)
            }
        }

}