package com.example.fixphone.fragment

import android.app.AlertDialog
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
import com.example.fixphone.DataStore.DataStoreManager.Companion.DEFAULT_ID
import com.example.fixphone.R
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentLoginBinding
import com.example.fixphone.viewmodel.FragmenLoginViewModel
import com.example.fixphone.viewmodel.HomeViewModel
import com.example.fixphone.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentLogin : Fragment() {
   private var _binding: FragmentLoginBinding? = null
   private val binding get() = _binding!!
//   private var phoneDatabase: PhoneDatabase? = null
//   lateinit var dataStore: DataStoreManager
   private val viewModel: FragmenLoginViewModel by viewModel()
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
//      phoneDatabase = PhoneDatabase.getInstance(requireContext())
//      dataStore = DataStoreManager(requireContext())
       viewModel.getUserPref()
       viewModel.userPref.observe(viewLifecycleOwner){
           if (it.id_user != DEFAULT_ID){
               Toast.makeText(requireContext(), "Selamat Data, ${it.nama}", Toast.LENGTH_SHORT).show()
               findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
           }
       }
      binding.tvBuat.setOnClickListener {
         findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
      }
      binding.btnLogin.setOnClickListener {
//         lifecycleScope.launch(Dispatchers.IO) {
//            val login = userDao()
//               ?.getUser(binding.etUsername.text.toString(), binding.etPassword.text.toString())
//            runBlocking(Dispatchers.Main) {
               when {
                  binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                     .isEmpty() -> {
                      AlertDialog.Builder(requireContext())
                          .setTitle("Alert")
                          .setMessage("Username/Password tidak boleh kosong!")
                          .setPositiveButton("OK"){ dialog, _ ->
                              dialog.dismiss()
                          }
                          .show()
                  }
                   else -> {
                       binding.tilPassword.error = ""
                       val username = binding.etUsername.text.toString()
                       val password = binding.etPassword.text.toString()
                       viewModel.loginUser(username, password)
                       viewModel.loginData.observe(viewLifecycleOwner){
                           if (it == null){
                               binding.tilPassword.error = "Username/password salah!"

                           } else {
                               viewModel.setUserPref(it)
                               if (findNavController().currentDestination?.id == R.id.fragmentLogin){
                                   findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                               }
                           }
                       }
                   }
               }
      }
       binding.tvBuat.setOnClickListener {
           findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
       }
   }

}

//                }
//                  login != null -> {
//                     viewModel.setDataUser(login)
//                     Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
//                     //findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
//                  }
//                  else -> {
//                     Toast.makeText(requireContext(), "Username/Password Salah", Toast.LENGTH_SHORT)
//                        .show()
//                  }
//               }
//            //}
//         //}
//      }
//   }

//   fun isLogin() {
//      viewModel.apply {
//         getDataUser().observe(viewLifecycleOwner) {
//            if (it.id_user != DataStoreManager.DEFAULT_ID) {
//               findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
//            }
//         }
//      }
//   }
//}

