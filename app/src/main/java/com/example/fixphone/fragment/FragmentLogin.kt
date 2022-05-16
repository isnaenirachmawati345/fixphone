package com.example.fixphone.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fixphone.DataStore.DataStoreManager
import com.example.fixphone.R
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentLoginBinding
import com.example.fixphone.viewmodel.HomeViewModel
import com.example.fixphone.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentLogin : Fragment() {
   private var _binding: FragmentLoginBinding? = null
   private val binding get() = _binding!!
   private var phoneDatabase: PhoneDatabase? = null
   lateinit var dataStore: DataStoreManager
   lateinit var viewModel: HomeViewModel
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = FragmentLoginBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      phoneDatabase = PhoneDatabase.getInstance(requireContext())
      dataStore = DataStoreManager(requireContext())
      viewModel = ViewModelProvider(
         requireActivity(),
         HomeViewModelFactory(dataStore)
      )[HomeViewModel::class.java]
      isLogin()
      binding.tvBuat.setOnClickListener {
         findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
      }
      binding.btnLogin.setOnClickListener {
         lifecycleScope.launch(Dispatchers.IO) {
            val login = phoneDatabase?.userDao()
               ?.getUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            runBlocking(Dispatchers.Main) {
               when {
                  binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                     .isEmpty() -> {

                  }
                  login != null -> {
                     viewModel.setDataUser(login)
                     Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                     //findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                  }
                  else -> {
                     Toast.makeText(requireContext(), "Username/Password Salah", Toast.LENGTH_SHORT)
                        .show()
                  }
               }
            }
         }
      }
   }

   fun isLogin() {
      viewModel.apply {
         getDataUser().observe(viewLifecycleOwner) {
            if (it.id_user != DataStoreManager.DEFAULT_ID) {
               findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
            }
         }
      }
   }
}

