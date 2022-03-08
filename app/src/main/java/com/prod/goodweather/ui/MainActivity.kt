package com.prod.goodweather.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.prod.goodweather.R
import com.prod.goodweather.databinding.ActivityMainBinding
import com.prod.goodweather.utils.checkAccessFineLocationPermission
import com.prod.goodweather.utils.requestPermissionWithRationale

class MainActivity : AppCompatActivity() {
	var _binding: ActivityMainBinding? = null
	val binding
		get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

	val navController: NavController by lazy {
		val navHostFragment = supportFragmentManager
			.findFragmentById(R.id.mainNavigationFragment) as NavHostFragment
		navHostFragment.navController
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		checkLocationAccess()
		setBottomNavigationController()
	}

	private fun setBottomNavigationController() {
		binding.bottomNavigation.setupWithNavController(navController)
	}

	private fun checkLocationAccess() {
		if (!applicationContext.checkAccessFineLocationPermission()) {
			permissionRequest()
		}
	}

	override fun onSupportNavigateUp() = navController.navigateUp()

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

	private fun permissionRequest() {
		val fineLocationRationalSnackbar = Snackbar.make(
			binding.mainNavigationFragment,
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