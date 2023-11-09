package com.example.healthapp01.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.healthapp01.R
import com.example.healthapp01.databinding.FragmentStoreBinding

// 스토어 프래그먼트
class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        // 식품 버튼 클릭 처리
        binding.foodBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jambaekee.com/"))
            startActivity(intent)
        }

        // 장비/의류 버튼 클릭 처리
        binding.fashionBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sharpapr.com/"))
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)
        }

        binding.plusTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_storeFragment_to_plusFragment)
        }

        binding.bookmarkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)
        }

        return binding.root

    }

}