package com.example.healthapp01.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.healthapp01.R
import com.example.healthapp01.comment.CommentLVAdapter
import com.example.healthapp01.comment.CommentModel
import com.example.healthapp01.databinding.ActivityBoardInsideBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

// 게시글 수정/삭제/댓글 처리
class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding: ActivityBoardInsideBinding

    private lateinit var key: String

    private val commentDataList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter: CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSetIcon.setOnClickListener{
            showDialog()
        }

        key = intent.getStringExtra("key").toString()
        //Toast.makeText(this, key, Toast.LENGTH_LONG).show()

        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener{
            insertComment(key)
        }

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter

        getCommentData(key)
    }

    // 댓글 가져오는 함수
    fun getCommentData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)
    }

    // 댓글 입력 처리하는 함수
    private fun insertComment(key: String){
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(binding.commentArea.text.toString(), FBAuth.getTime())
            )
        Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")
    }

    // 게시글 수정or삭제 함수
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener{
            //Toast.makeText(this, "수정", Toast.LENGTH_LONG).show()
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.delBtn)?.setOnClickListener{
            //Toast.makeText(this, "삭제", Toast.LENGTH_LONG).show()
            FBRef.boardRef.child(key).removeValue()
            finish()
        }

    }

    // 이미지 데이터 가져오는 함수
    private fun getImageData(key: String){
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task->
            if(task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            }
            else{
                binding.getImageArea.isVisible = false
            }

        })
    }

    // Uid값이 일치하면 게시글 삭제하고 데이터 가져오는 함수
    private fun getBoardData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try{
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                    Log.d(TAG, dataModel!!.title)

                    binding.titleArea.text = dataModel!!.title
                    binding.contentArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid

                    if(myUid.equals(writerUid)){
                        //Toast.makeText(baseContext, "글쓴이 O", Toast.LENGTH_LONG).show()
                        binding.boardSetIcon.isVisible = true
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
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}