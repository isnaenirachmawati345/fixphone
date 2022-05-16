package com.example.fixphone.fragment

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
import com.example.fixphone.databinding.FragmentRegisterBinding
import com.example.fixphone.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FragmentRegister : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var phoneDatabase : PhoneDatabase?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneDatabase = PhoneDatabase.getInstance(requireContext())
        binding.tvBelom.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
        }
        binding.btnRegister.setOnClickListener {
            when {
                binding.etUsername.text.toString().isEmpty()|| binding.etPassword.text.toString().isEmpty() || binding.etNama.text.toString().isEmpty() || binding.etEmail.toString().isEmpty() || binding.etConfirmPassword.text.toString().isEmpty() -> {
                 Toast.makeText(requireContext(), "Maaf From Kosong!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val data = User(
                        null,
                        binding.etNama.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etUsername.text.toString(),
                        binding.etPassword.text.toString(),
                        "no_image"
                    )
                    lifecycleScope.launch(Dispatchers.IO){
                        val  register = phoneDatabase?.userDao()?.insertUser(data)
                        runBlocking(Dispatchers.Main){
                            if (register != 0.toLong()){
                                Toast.makeText(requireContext(), "Berhasil Registrasi", Toast.LENGTH_SHORT ).show()
                                findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
                            } else {
                                Toast.makeText(requireContext(), "Registrasi Gagal", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

}