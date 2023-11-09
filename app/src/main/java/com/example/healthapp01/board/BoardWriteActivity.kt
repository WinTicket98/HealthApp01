package com.example.healthapp01.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthapp01.R
import com.example.healthapp01.contentList.BookmarkModel
import com.example.healthapp01.databinding.ActivityBoardWriteBinding
import com.example.healthapp01.utils.FBAuth
import com.example.healthapp01.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

// 게시글 작성 처리
class BoardWriteActivity : AppCompatActivity() {

    private var TAG = BoardWriteActivity::class.simpleName

    private lateinit var defaultImage: Bitmap

    private lateinit var binding: ActivityBoardWriteBinding

    private var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener{
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG, title)
            Log.d(TAG, content)

            // 이미지 이름을 문서의 key 값으로 설정
            val key = FBRef.boardRef.push().key.toString()

            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, content, uid, time))

            Toast.makeText(this, "게시글 입력 완료!", Toast.LENGTH_LONG).show()

            if(isImageUpload){
                val imageView: ImageView = binding.imageArea
                val chagedImage: BitmapDrawable = imageView.drawable as BitmapDrawable
                val bitmap: Bitmap = chagedImage.bitmap

                // 이미지를 선택하지 않았으면 defaultImage로 세팅
                if(bitmap != defaultImage){
                    imageUpload(key)
                }
                else{
                    //Toast.makeText(this, "No image!", Toast.LENGTH_LONG).show()
                }
            }

            finish()
        }

        binding.imageArea.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true

            val imageView: ImageView = binding.imageArea
            defaultImage = (imageView.drawable as BitmapDrawable).bitmap
        }
    }

    // 이미지 업로드 처리하는 함수
    private fun imageUpload(key: String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png")

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    // 액티비티 결과 전달
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            binding.imageArea.setImageURI(data?.data)
        }

    }
}