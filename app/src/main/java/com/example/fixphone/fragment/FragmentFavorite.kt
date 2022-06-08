package com.example.fixphone.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fixphone.DataStore.DataStoreManager.Companion.DEFAULT_ID
import com.example.fixphone.R
import com.example.fixphone.adapter.FavoriteAdapter
import com.example.fixphone.database.PhoneDatabase
import com.example.fixphone.databinding.FragmentFavoriteBinding
import com.example.fixphone.model.Phone
import com.example.fixphone.viewmodel.FavoriteEntity
import com.example.fixphone.viewmodel.FavoriteFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteFragmentViewModel by viewModel()
    private var idUser = DEFAULT_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserPref()
        viewModel.userPref.observe(viewLifecycleOwner){
            if (it != null){
                idUser = it.id_user!!
                binding.tvWelcome.text = getString(R.string.favorite, it.nama)
                viewModel.getDataFavorite(idUser)
            }
        }

        viewModel.dataFavorite.observe(viewLifecycleOwner){
            when {
                it.isEmpty() -> {
                    binding.apply {
                        tvWelcome.visibility = View.VISIBLE
                        pbMain.visibility = View.GONE
                    }
                }
                else -> {
                    binding.pbMain.visibility = View.GONE
                    val adapter = FavoriteAdapter(object : FavoriteAdapter.OnClickListener{
                        override fun onClickItem(data: FavoriteEntity) {
                            val dataDetail = Phone(
                                data.image,
                                data.thumbnail,
                                data.detail,
                                data.slug
                            )
                            val moveToDetail = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(dataDetail)
                            findNavController().navigate(moveToDetail)
                        }
                    })
                    adapter.submitData(it)
                    binding.rvMain.adapter = adapter
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
    }
}