package com.example.healthapp01.contentList

// 컨텐츠 모델
data class ContentModel(
    var title: String = "",      // 제목
    var imageUrl: String = "",   // 이미지 주소
    var webUrl: String = ""      // 웹 주소
)