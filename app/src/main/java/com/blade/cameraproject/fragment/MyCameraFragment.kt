package com.blade.cameraproject.fragment

import androidx.navigation.fragment.findNavController
import com.blade.cameralib.fragment.CameraFragment
import com.blade.cameralib.utils.CaptureResultData

class MyCameraFragment : CameraFragment() {
    override fun onResult(result: CaptureResultData) {
        findNavController().navigate(
            MyCameraFragmentDirections.actionCameraFragmentToImageViewerFragment(
                result.filePath
            )
                .setOrientation(result.orientation)
        )
    }
}