package com.example.healthapp01.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.healthapp01.R
import com.example.healthapp01.databinding.ActivityBoardEditBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

// 게시글 수정
class BoardEditActivity : AppCompatActivity() {

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var binding: ActivityBoardEditBinding

    private lateinit var key: String

    //private lateinit var writerUid: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener{
            editBoardData(key)
        }
    }

    // 게시글 수정 함수
    private fun editBoardData(key: String){
       FBRef.boardRef
           .child(key)
           .setValue(BoardModel(binding.titleArea.text.toString()
               , binding.contentArea.text.toString()
               , FBAuth.getUid()
               , FBAuth.getTime())
           )

        Toast.makeText(this, "수정 완료!", Toast.LENGTH_LONG).show()
        finish()
    }

    // 이미지 데이터 가져오는 함수
    private fun getImageData(key: String){
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            }
            else{ }

        })
    }

    // 제목 내용 가져오는 함수
    private fun getBoardData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.content)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}