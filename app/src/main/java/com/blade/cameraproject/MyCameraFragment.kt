package com.blade.cameraproject

import android.util.Log
import com.blade.cameralib.CameraFragment
import com.blade.cameralib.CaptureResultData

class MyCameraFragment : CameraFragment() {
    override fun onResult(result: CaptureResultData) {
        Log.d("MiaNatha", result.filePath)
    }
}