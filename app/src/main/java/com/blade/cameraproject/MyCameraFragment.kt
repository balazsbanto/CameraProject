package com.blade.cameraproject

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.blade.cameralib.CameraFragment
import com.blade.cameralib.CaptureResultData

class MyCameraFragment : CameraFragment() {
    override fun onResult(result: CaptureResultData) {
        Log.d("MiaNatha", result.filePath)
        findNavController().navigate(
            MyCameraFragmentDirections
                .actionCameraFragmentToImageViewerFragment(result.filePath)
                .setOrientation(result.orientation)
        )
    }
}