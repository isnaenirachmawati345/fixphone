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
import androidx.navigation.fragment.navArgs
import com.example.fixphone.R
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentEditBinding
import com.example.fixphone.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Fragmentedit : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val args: FragmenteditArgs by navArgs()
    private var phoneDatabase: PhoneDatabase?? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneDatabase = PhoneDatabase.getInstance(requireContext())
        val sharedPreferences =
            requireContext().getSharedPreferences(FragmentLogin.SHARED_FILE, Context.MODE_PRIVATE)
        val dataUser = args.user
        binding.apply {
            etNama.setText(dataUser.nama)
            etEmail.setText(dataUser.email)
            etUsername.setText(dataUser.username)
            etPassword.setText(dataUser.password)
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentedit_to_fragmentHome)
        }
        binding.btnEdit.setOnClickListener {
            when {
                binding.etNama.text.toString().isEmpty() ||
                        binding.etEmail.text.toString().isEmpty() ||
                        binding.etUsername.text.toString().isEmpty() ||
                        binding.etPassword.text.toString().isEmpty() -> {
                    Toast.makeText(requireContext(), "Form tidak boleh kosong!", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    val user = User(
                        dataUser.id_user,
                        binding.etNama.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etUsername.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    lifecycleScope.launch(Dispatchers.IO){
                        val result = phoneDatabase?.userDao()?.updateUser(user)
                        runBlocking(Dispatchers.Main){
                            if (result != 0) {
                                val editor = sharedPreferences.edit()
                                editor.putString("username", user.username)
                                editor.putString("password", user.password)
                                editor.apply()
                                Toast.makeText(
                                    requireContext(),
                                    "Data berhasil di Update!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_fragmentedit_to_fragmentHome)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Data gagal di Update!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }


        }
    }
}