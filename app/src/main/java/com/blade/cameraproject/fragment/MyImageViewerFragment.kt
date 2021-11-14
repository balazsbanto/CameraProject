package com.blade.cameraproject.fragment

import androidx.navigation.fragment.navArgs
import com.blade.cameralib.fragment.ImageViewerFragment

class MyImageViewerFragment : ImageViewerFragment() {

    private val args: MyImageViewerFragmentArgs by navArgs()

    override var filePath: String
        get() = args.filePath
        set(value) {}
    override var orientation: Int
        get() = args.orientation
        set(value) {}
}