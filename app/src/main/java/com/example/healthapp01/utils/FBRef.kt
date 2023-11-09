package com.example.healthapp01.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// Firebase database 접근
class FBRef {

    companion object{

        private val database = Firebase.database

        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")
        val category3 = database.getReference("contents3")
        val category4 = database.getReference("contents4")
        val category5 = database.getReference("contents5")
        val category6 = database.getReference("contents6")
        val category7 = database.getReference("contents7")
        val category8 = database.getReference("contents8")
        val category9 = database.getReference("contents9")

        val bookmarkRef = database.getReference("bookmark_list")

        val boardRef = database.getReference("board")

        val commentRef = database.getReference("comment")

        val profileRef = database.getReference("profile")
    }
}