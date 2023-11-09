package com.example.healthapp01.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.healthapp01.R
import com.example.healthapp01.contentList.ContentListActivity
import com.example.healthapp01.databinding.FragmentTipBinding
import java.util.zip.Inflater

// 팁 프래그먼트
class TipFragment : Fragment() {

    private lateinit var binding: FragmentTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)

        // category 클릭 처리
        binding.category1.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category1")
            startActivity(intent)
        }

        binding.category2.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category2")
            startActivity(intent)
        }

        binding.category3.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category3")
            startActivity(intent)
        }

        binding.category4.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category4")
            startActivity(intent)
        }

        binding.category5.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category5")
            startActivity(intent)
        }

        binding.category6.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category6")
            startActivity(intent)
        }

        binding.category7.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category7")
            startActivity(intent)
        }

        binding.category8.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category8")
            startActivity(intent)
        }

        binding.category9.setOnClickListener{
            val intent = Intent(context, ContentListActivity::class.java)
            intent.putExtra("category","category9")
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)
        }

        binding.plusTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment_to_plusFragment)
        }

        binding.bookmarkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)
        }

        return binding.root
    }

}