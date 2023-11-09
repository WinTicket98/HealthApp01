package com.example.healthapp01.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.healthapp01.R
import com.example.healthapp01.board.ProfileListLVAdapter
import com.example.healthapp01.databinding.FragmentHomeBinding
import com.example.healthapp01.profile.ProfileInsideActivity
import com.example.healthapp01.profile.ProfileModel
import com.example.healthapp01.profile.ProfileWriteActivity
import com.example.healthapp01.setting.SettingActivity
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

// 홈 프래그먼트
class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.java.simpleName

    private lateinit var binding: FragmentHomeBinding

    private val profileDataList = mutableListOf<ProfileModel>()
    private val profileKeyList = mutableListOf<String>()

    private lateinit var profileRVAdapter: ProfileListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        profileRVAdapter = ProfileListLVAdapter(profileDataList)
        binding.profileListView.adapter = profileRVAdapter

        // 데이터베이스에 이메일이 널값이 아니면 닉네임 설정
        if(FBAuth.getEmail() != null){
            var userName = FBAuth.getEmail()
            var uN: List<String> = userName.split("@")
            binding.userName.text = uN[0]
        }

        binding.profileListView.setOnItemClickListener{ parent, view, position, id ->

            val intent = Intent(context, ProfileInsideActivity::class.java)
            intent.putExtra("key", profileKeyList[position])
            startActivity(intent)
        }

        binding.addRoutine.setOnClickListener{
            val intent = Intent(context, ProfileWriteActivity::class.java)
            startActivity(intent)
        }

        binding.tipTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }
        binding.plusTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_plusFragment)
        }
        binding.bookmarkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }
        binding.storeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }

        getFBProfileData()

        return binding.root
    }

    private fun getFBProfileData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                profileDataList.clear()

                for(dataModel in dataSnapshot.children){
                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(ProfileModel::class.java)

                    val myUid = FBAuth.getUid()
                    val writerUid = item?.uid

                    // uid값 비교해서 동일하면 리스트에 값 추가 / 프로필 데이터리스트 사이즈값을 루틴 개수로 설정
                    if(myUid.equals(writerUid)){
                        profileDataList.add(item!!)
                        profileKeyList.add(dataModel.key.toString())
                        binding.routineCount.text = profileDataList.size.toString()
                    }

                }
                profileKeyList.reverse()
                profileDataList.reverse()
                profileRVAdapter.notifyDataSetChanged()

                Log.d(TAG, profileDataList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.profileRef.addValueEventListener(postListener)
    }

}
