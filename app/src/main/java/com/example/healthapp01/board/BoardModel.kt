package com.example.healthapp01.board

// 게시글 모델
data class BoardModel(
    val title: String = "",   // 제목
    val content: String = "", // 내용 
    val uid: String = "",     // uid값
    val time: String = ""     // 시간
)