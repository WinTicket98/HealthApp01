package com.example.healthapp01.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthapp01.R
import com.example.healthapp01.databinding.ActivityProfileEditBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileEditActivity : AppCompatActivity() {

    private val TAG = ProfileEditActivity::class.java.simpleName

    private lateinit var binding: ActivityProfileEditBinding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)

        key = intent.getStringExtra("key").toString()
        getProfileData(key)

        binding.editBtn.setOnClickListener{
            editProfileData(key)
        }
    }

    private fun editProfileData(key: String){
        FBRef.profileRef
            .child(key)
            .setValue(ProfileModel(binding.titleArea.text.toString()
                , binding.contentArea.text.toString()
                , FBAuth.getUid()
                , FBAuth.getTime()
            ))
        Toast.makeText(this, "수정 완료!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun getProfileData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(ProfileModel::class.java)
                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.profileRef.child(key).addValueEventListener(postListener)
    }
}