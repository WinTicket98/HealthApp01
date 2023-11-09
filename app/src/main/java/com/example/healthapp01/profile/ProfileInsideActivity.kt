package com.example.healthapp01.profile

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.healthapp01.R
import com.example.healthapp01.databinding.ActivityProfileInsideBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileInsideBinding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_inside)

        binding.ProfileSetIcon.setOnClickListener{
            showDialog()
        }

        key = intent.getStringExtra("key").toString()

        getProfileData(key)
    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Routine 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener{
            //Toast.makeText(this, "수정", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProfileEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.delBtn)?.setOnClickListener{
            //Toast.makeText(this, "삭제", Toast.LENGTH_LONG).show()
            FBRef.profileRef.child(key).removeValue()
            finish()
        }

    }

    private fun getProfileData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try{
                    val dataModel = dataSnapshot.getValue(ProfileModel::class.java)

                    binding.titleArea.text = dataModel!!.title
                    binding.contentArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)){
                        //Toast.makeText(baseContext, "글쓴이 O", Toast.LENGTH_LONG).show()
                        binding.ProfileSetIcon.isVisible = true
                    }
                    else{
                        //Toast.makeText(baseContext, "글쓴이 X", Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: Exception){
                    Log.d(TAG, "삭제완료!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.profileRef.child(key).addValueEventListener(postListener)
    }
}