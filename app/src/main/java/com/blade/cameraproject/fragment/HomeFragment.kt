package com.blade.cameraproject.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blade.cameraproject.R
import com.blade.cameraproject.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    lateinit var binding:HomeFragmentBinding
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

        //TODO STEP 5 - Set an OnClickListener, using Navigation.createNavigateOnClickListener()

        binding.startCameraButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_permissions_fragment)
        }
        //TODO END STEP 5

        //TODO STEP 6 - Set NavOptions
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }
//        view.findViewById<Button>(R.id.navigate_destination_button)?.setOnClickListener {
//            findNavController().navigate(R.id.flow_step_one_dest, null, options)
//        }
        //TODO END STEP 6

//        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
//        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        )

//        // Note the usage of curly braces since we are defining the click listener lambda
//        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener {
//            val flowStepNumberArg = 1
//            val action = HomeFragmentDirections.nextAction(flowStepNumberArg)
//            findNavController().navigate(action)
//        }

        //TODO END STEP 7.2
    }
}