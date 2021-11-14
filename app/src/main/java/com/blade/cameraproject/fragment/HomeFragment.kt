package com.blade.cameraproject.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blade.cameraproject.R
import com.blade.cameraproject.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startCameraButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_permissions_fragment)
        }
    }
}