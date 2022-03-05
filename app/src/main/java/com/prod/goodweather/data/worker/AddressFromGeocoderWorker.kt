package com.prod.goodweather.data.worker

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.work.*

class AddressFromGeocoderWorker(val context: Context, val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val lat = workerParameters.inputData.getDouble(LATINTUDE, 0.0)
        val lon = workerParameters.inputData.getDouble(LONGITUDE, 0.0)
        Log.d("AddressFromLocationWorker","AddressFromLocationWorker")
        if (Geocoder.isPresent()) {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (!addresses.isEmpty()) {
                val address:Address = addresses[0]
                val output : Data = workDataOf(
                    COUNTRY_NAME to address.countryName,
                    ADMIN_AREA to address.adminArea,
                    SUB_ADMIN_AREA to address.subAdminArea,
                    LOCALITY to address.locality,
                    THOROUGHFARE to address.thoroughfare,
                )
                    Data.Builder()
                    .putString(LOCALITY, address.locality)
                    .build()
                return Result.success(output)
            }
            throw RuntimeException("Address isEmpty")
        }
        throw RuntimeException("Geocoder not present")

    }

    companion object {
        private const val LATINTUDE = "latintude"
        private const val LONGITUDE = "longitude"
        const val COUNTRY_NAME = "countryName"
        const val ADMIN_AREA = "adminArea"
        const val SUB_ADMIN_AREA = "subAdminArea"
        const val LOCALITY = "locality"
        const val THOROUGHFARE = "thoroughfare"
        const val WORK_NAME = "AddressFromLocation"
        fun makeRequest(lat: Double, lon: Double): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<AddressFromGeocoderWorker>()
                .setInputData(workDataOf(LATINTUDE to lat, LONGITUDE to lon))
                //.setConstraints(makeConstraints())
                .build()
        }
        private fun makeConstraints(): Constraints = Constraints.Builder()
            .build()
    }
}