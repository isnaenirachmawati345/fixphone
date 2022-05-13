package com.example.fixphone.fragment

import android.annotation.SuppressLint
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
import com.example.fixphone.R
import com.example.fixphone.adapter.MainAdapter
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentHomeBinding
import com.example.fixphone.model.GetLatestPhoneResponse
import com.example.fixphone.model.Phone
import com.example.fixphone.model.User
import com.example.fixphone.service.ApiClient
import com.example.fixphone.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var phoneDatabase: PhoneDatabase? = null
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        phoneDatabase = PhoneDatabase.getInstance(requireContext())
        val sharePreferences =
            requireContext().getSharedPreferences(FragmentLogin.SHARED_FILE, Context.MODE_PRIVATE)
        val username = sharePreferences.getString("username", "default_username")
        val password = sharePreferences.getString("password", "default_password")
        homeViewModel.userData.observe(viewLifecycleOwner) {
            binding.tvWelcome.text = getString(R.string.welcome, it.nama)
        }
        if (username != null) {
            if (password != null) {
                getUserData(username, password)
            }
        }
        binding.toolbar.setOnClickListener {
            homeViewModel.userData.observe(viewLifecycleOwner) {
                AlertDialog.Builder(requireContext())
                    .setTitle("User Profile")
                    .setMessage(
                        """
                        Nama : ${it.nama}
                        Email: ${it.email}
                        Username: ${it.username}
                        """.trimIndent()
                    )
                    .setNeutralButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Logout") { dialog, _ ->
                        AlertDialog
                            .Builder(requireContext())
                            .setTitle("Konfirmasi Logout")
                            .setMessage("Yakin Mau Keluar?")
                            .setNeutralButton("Tidak") { dialog, _ -> dialog.dismiss() }
                            .setPositiveButton("Iya") { dialog, _ ->
                                val edit = sharePreferences.edit()
                                edit.clear()
                                edit.apply()
                                dialog.dismiss()
                                findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
                            }
                            .create()
                            .show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Edit Profile") { _, _ ->
                        val data = User(
                            it.id_user,
                            it.nama,
                            it.email,
                            it.username,
                            it.password
                        )
                        val editor = FragmentHomeDirections.actionFragmentHomeToFragmentedit(data)
                        findNavController().navigate(editor)

                    }
                    .create().show()
            }
        }
        getCases()
        fetchAllData()
    }

    private fun getUserData(username: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val getdata = phoneDatabase?.userDao()?.getUser(username, password)
            runBlocking(Dispatchers.Main) {
                if (getdata != null) {
                    homeViewModel.getUserData(getdata)
                }
            }
        }
    }

    private fun getCases() {
        ApiClient.instance.getLatestPhone().enqueue(object : Callback<GetLatestPhoneResponse> {
            @SuppressLint("StringFormatInvalid")
            override fun onResponse(
                call: Call<GetLatestPhoneResponse>,
                response: Response<GetLatestPhoneResponse>
            ) {
                binding.tvMessage.text =
                    getString(R.string.silahkan_dipilih, response.body()?.status.toString())
            }

            override fun onFailure(call: Call<GetLatestPhoneResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun fetchAllData() {
        ApiClient.instance.getLatestPhone()
            .enqueue(object : Callback<GetLatestPhoneResponse> {
                override fun onResponse(
                    call: Call<GetLatestPhoneResponse>,
                    response: Response<GetLatestPhoneResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        showList(body?.data?.phones)
                        binding.pbMain.visibility = View.GONE
                    } else {
                        binding.pbMain.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<GetLatestPhoneResponse>, t: Throwable) {
                    binding.pbMain.visibility = View.GONE
                }

            })

    }

    private fun showList(data:  List<Phone>?) {
       val adapter = MainAdapter(object  : MainAdapter.OnClickListener{
           override fun onClickItem(data: Phone) {
               val toDeDetail = FragmentHomeDirections.actionFragmentHomeToFragmentDetail(data.slug)
               findNavController().navigate(toDeDetail)
           }
       })
        adapter.submitData(data)
        binding.rvMain.adapter = adapter
    }
}






