package com.zhalz.voyageoflife.ui.maps

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.zhalz.voyageoflife.R
import com.zhalz.voyageoflife.data.remote.response.StoryData
import com.zhalz.voyageoflife.databinding.ActivityMapsBinding
import com.zhalz.voyageoflife.utils.ApiResult
import com.zhalz.voyageoflife.utils.FileHelper.vectorToBitmap
import com.zhalz.voyageoflife.utils.ToastMaker.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding: ActivityMapsBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_maps) }
    private val viewModel: MapsViewModel by viewModels()

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.run {
            isCompassEnabled = true
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isMapToolbarEnabled = true
        }

        setMapStyle()
        collectStories()
    }

    private fun collectStories() = lifecycleScope.launch {
        viewModel.storiesResponse.collect {
            when (it) {
                is ApiResult.Success -> showMarker(it.data?.listStory ?: emptyList())
                is ApiResult.Error -> toast(it.message)
                is ApiResult.Loading -> {}
            }
        }
    }

    private fun setMapStyle() {
        try {
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        }
        catch (exception: Resources.NotFoundException) {
            toast(resources.getString(R.string.style_error))
        }
    }

    private fun showMarker(stories: List<StoryData>) {
        val firstStory = stories.firstOrNull() ?: return
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            LatLng(firstStory.lat ?: return,  firstStory.lon ?: return), 10f
        )
        map.animateCamera(cameraUpdate)

        stories.forEach { story ->
            val latLng = LatLng(story.lat ?: return, story.lon ?: return)

            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(vectorToBitmap(R.drawable.ic_circular_user, this@MapsActivity))
                    .title(story.name)
                    .snippet(story.description)
            )
        }
    }

}