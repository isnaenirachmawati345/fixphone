package com.example.fixphone.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fixphone.R
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentLogin : Fragment() {
   private var _binding: FragmentLoginBinding? = null
   private val binding get() = _binding!!
   private var phoneDatabase: PhoneDatabase? = null
   companion object{
      const val SHARED_FILE = "kotlinsharepreferences"
   }

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
      val sharedPreferences = requireContext().getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE)
      val cekUser = sharedPreferences.getString("username", "default_username")
      if (cekUser != "default_username"){
         findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
      }
      binding.tvBuat.setOnClickListener {
         findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
      }
      binding.btnLogin.setOnClickListener {
         lifecycleScope.launch(Dispatchers.IO){
            val login = phoneDatabase?.userDao()?.loginUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            runBlocking (Dispatchers.Main){
               when{
                  binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty() -> {

                  }
                  login == true -> {
                     val edit = sharedPreferences.edit()
                     edit.putString("username", binding.etUsername.text.toString())
                     edit.putString("password" , binding.etPassword.text.toString())
                     edit.apply()
                     Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT). show()
                     findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                  }
                  else -> {
                     Toast.makeText(requireContext(), "Username/Password Salah", Toast.LENGTH_SHORT).show()
                  }
               }
            }
         }
      }
      }
   }
