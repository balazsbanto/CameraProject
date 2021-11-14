package com.blade.cameralib.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.blade.cameralib.databinding.FragmentImageViewerBinding
import com.blade.cameralib.utils.decodeExifOrientation
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import kotlin.math.max


abstract class ImageViewerFragment : Fragment() {

    lateinit var binding:FragmentImageViewerBinding

    /** AndroidX navigation arguments */
//    private val args: ImageViewerFragmentArgs by navArgs()

    /** Default Bitmap decoding options */
    private val bitmapOptions = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        // Keep Bitmaps at less than 1 MP
        if (max(outHeight, outWidth) > DOWNSAMPLE_SIZE) {
            val scaleFactorX = outWidth / DOWNSAMPLE_SIZE + 1
            val scaleFactorY = outHeight / DOWNSAMPLE_SIZE + 1
            inSampleSize = max(scaleFactorX, scaleFactorY)
        }
    }

    /** Bitmap transformation derived from passed arguments */
    private val bitmapTransformation: Matrix by lazy { decodeExifOrientation(orientation) }


    /** Data backing our Bitmap viewpager */
    private val bitmapList: MutableList<Bitmap> = mutableListOf()

    private fun imageViewFactory() = ImageView(requireContext()).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }


//    = ViewPager2(requireContext()).apply {
//        // Populate the ViewPager and implement a cache of two media items
//        offscreenPageLimit = 2
//        adapter = GenericListAdapter(
//            bitmapList,
//            itemViewFactory = { imageViewFactory() }) { view, item, _ ->
//            view as ImageView
//            Glide.with(view).load(item).into(view)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {

            // Load input image file
            val inputBuffer = loadInputBuffer()

            // Load the main JPEG image
            withContext(Dispatchers.Main) {
                Glide.with(binding.imageViewer)
                    .load(decodeBitmap(inputBuffer, 0, inputBuffer.size))
                    .into(binding.imageViewer)
            }

//            addItemToViewPager(view, decodeBitmap(inputBuffer, 0, inputBuffer.size))

        }
    }

    /** Utility function used to read input file into a byte array */
    private fun loadInputBuffer(): ByteArray {
        val inputFile = File(filePath)
        return BufferedInputStream(inputFile.inputStream()).let { stream ->
            ByteArray(stream.available()).also {
                stream.read(it)
                stream.close()
            }
        }
    }

    /** Utility function used to decode a [Bitmap] from a byte array */
    private fun decodeBitmap(buffer: ByteArray, start: Int, length: Int): Bitmap {

        // Load bitmap from given buffer
        val bitmap = BitmapFactory.decodeByteArray(buffer, start, length, bitmapOptions)

        // Transform bitmap orientation using provided metadata
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, bitmapTransformation, true)
    }

    companion object {
        private val TAG = ImageViewerFragment::class.java.simpleName
        private const val ARG_FILEPATH = "arg_filepath"
        private const val ARG_ORIENTATION = "arg_orientation"

//        @JvmStatic
//        fun newInstance(filePath: String, orientation:Int) = ImageViewerFragment().apply {
//            arguments = Bundle().apply {
//                putString(ARG_FILEPATH, filePath)
//                putInt(ARG_ORIENTATION, orientation)
//            }
//        }

        /** Maximum size of [Bitmap] decoded */
        private const val DOWNSAMPLE_SIZE: Int = 1024  // 1MP

    }

    abstract var filePath:String
    abstract var orientation:Int

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        arguments?.getString(ARG_FILEPATH)?.let {
//            filePath = it
//        }
//
//        arguments?.getInt(ARG_ORIENTATION)?.let {
//            orientation = it
//        }
//    }
}