package com.example.fixphone.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fixphone.DataStore.DataStoreManager.Companion.DEFAULT_ID
import com.example.fixphone.R
import com.example.fixphone.databinding.FragmentDetailBinding
import com.example.fixphone.model.GetPhoneSpecsResponse
import com.example.fixphone.service.ApiClient
import com.example.fixphone.viewmodel.DetailFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class FragmentDetail : Fragment() {
     private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentDetailArgs by  navArgs()
    private val detailViewModel: DetailFragmentViewModel by viewModel()
    private var idUser = DEFAULT_ID
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //detailVm = ViewModelProvider(requireActivity()).get(detailViewModel::class.java)
        val slug = args.slug
        detailViewModel.getUserPref()
        detailViewModel.userPref.observe(viewLifecycleOwner){
            if (it.id_user != DEFAULT_ID){
                idUser = it.id_user!!
                detailViewModel.checkFavorite(idUser, slug)
            }
            binding.apply {
                Glide.with(binding.root)
                    .load(it.data.thumbnail)
                    .into(ivPhoneImage)
                tvPhoneName.text = it.data.phoneName
            }
        }
    }

    private fun getPhoneSpecs(slug: String) {
        ApiClient.instance.getSpecsPhoneById(slug).enqueue(object  : Callback<GetPhoneSpecsResponse>{
            override fun onResponse(
                call: Call<GetPhoneSpecsResponse>,
                response: Response<GetPhoneSpecsResponse>
            ) {
                if (response.code() == 200){
                    detailViewModel.phoneSpecs.postValue(response.body())
                    binding.pbDetail.visibility = View.GONE
                    binding.clDetail.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<GetPhoneSpecsResponse>, t: Throwable) {
                Log.d("DetailViewModel", "${t.message}")
            }

        }
        )

    }

}