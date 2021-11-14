package com.blade.cameralib.utils

import android.annotation.SuppressLint
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata

object CameraUtils {

    /** Helper class used as a data holder for each selectable camera format item */
    data class FormatItem(val orientation: Int, val cameraId: String, val format: Int)

    /** Helper function used to convert a lens orientation enum into a human-readable string */
    private fun lensOrientationString(value: Int) = when(value) {
        CameraCharacteristics.LENS_FACING_BACK -> "Back"
        CameraCharacteristics.LENS_FACING_FRONT -> "Front"
        CameraCharacteristics.LENS_FACING_EXTERNAL -> "External"
        else -> "Unknown"
    }

    @SuppressLint("InlinedApi")
    private fun enumerateCameras(cameraManager: CameraManager): List<FormatItem> {
        val availableCameras: MutableList<FormatItem> = mutableListOf()

        // Get list of all compatible cameras
        val cameraIds = cameraManager.cameraIdList.filter {
            val characteristics = cameraManager.getCameraCharacteristics(it)
            val capabilities = characteristics.get(
                CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
            capabilities?.contains(
                CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE) ?: false
        }


        // Iterate over the list of cameras and return all the compatible ones
        cameraIds.forEach { id ->
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val orientation = characteristics.get(CameraCharacteristics.LENS_FACING)!!

            // Query the available capabilities and output formats
            val capabilities = characteristics.get(
                CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)!!
            val outputFormats = characteristics.get(
                CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!.outputFormats

            // All cameras *must* support JPEG output so we don't need to check characteristics
            availableCameras.add(FormatItem(orientation, id, ImageFormat.JPEG))

            // Return cameras that support RAW capability
            if (capabilities.contains(
                    CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW) &&
                outputFormats.contains(ImageFormat.RAW_SENSOR)) {
                availableCameras.add(FormatItem(orientation, id, ImageFormat.RAW_SENSOR))
            }

            // Return cameras that support JPEG DEPTH capability
            if (capabilities.contains(
                    CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT) &&
                outputFormats.contains(ImageFormat.DEPTH_JPEG)) {
                availableCameras.add(FormatItem(orientation, id, ImageFormat.DEPTH_JPEG))
            }
        }

        return availableCameras
    }

    fun getBackJpgCamera(cameraManager: CameraManager): FormatItem {
        val cameraList = enumerateCameras(cameraManager)
        return cameraList.first {
            it.orientation == CameraCharacteristics.LENS_FACING_BACK &&
                    it.format == ImageFormat.JPEG
        }
    }
}