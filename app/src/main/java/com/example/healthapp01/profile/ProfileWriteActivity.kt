package com.example.healthapp01.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthapp01.R
import com.example.healthapp01.databinding.ActivityProfileWriteBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef

class ProfileWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_write)

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            val key = FBRef.profileRef.push().key.toString()

            FBRef.profileRef
                .child(key)
                .setValue(ProfileModel(title, content, uid, time))

            //Toast.makeText(this, "My Routine 입력 완료!", Toast.LENGTH_LONG).show()
            finish()
        }

    }

}