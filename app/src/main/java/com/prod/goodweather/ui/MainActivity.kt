package com.prod.goodweather.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.prod.goodweather.R
import com.prod.goodweather.databinding.ActivityMainBinding
import com.prod.goodweather.ui.fragment.HomeFragment
import com.prod.goodweather.utils.hasPermission
import com.prod.goodweather.utils.requestPermissionWithRationale
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkPermission()) {
            permissionRequest()
            waitPermission()
        } else {
            launchFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun waitPermission() {
        thread {
            Thread.sleep(5000)
            if (checkPermission()) {
                launchFragment()
            } else {
                runOnUiThread {
                    waitPermission()
                }
            }
        }
    }

    private fun launchFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment.newInstance())
            .commit()
    }

    private fun checkPermission(): Boolean =
        applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun permissionRequest() {
        val fineLocationRationalSnackbar = Snackbar.make(
            binding.fragmentContainer,
            R.string.fine_location_permission_rationale,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.ok) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
            )
        }
        requestPermissionWithRationale(
            Manifest.permission.ACCESS_FINE_LOCATION,
            REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE,
            fineLocationRationalSnackbar
        )
    }


    companion object {
        private const val REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE = 34
    }
}