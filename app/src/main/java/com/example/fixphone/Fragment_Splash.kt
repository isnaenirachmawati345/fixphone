package com.example.fixphone

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fixphone.MainActivity.Companion.SHARED_PREFERENCES



class Fragment_Splash : Fragment() {
    //    private lateinit var viewModel: HomeViewModel
//    lateinit var dataStore: DataStoreManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //dataStore = DataStoreManager(requireContext())
        //viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(dataStore))[HomeViewModel :: class.java]
        val sharedPreference = context?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        val sharedPreferences = sharedPreference?.getString("username", "")
        Handler(Looper.getMainLooper()).postDelayed({
            // viewModel.apply {
            // getDataUser().observe(viewLifecycleOwner){
            if (sharedPreferences == "") {
                val direct = Fragment_SplashDirections.actionFragmentSplashToFragmentLogin()
                findNavController().navigate(direct)
            } else {
                val direct = Fragment_SplashDirections.actionFragmentSplashToFragmentHome()
                findNavController().navigate(direct)
            }
        }, 2000)
    }
}
