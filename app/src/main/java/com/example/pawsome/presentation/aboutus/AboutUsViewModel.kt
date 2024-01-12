package com.example.pawsome.presentation.aboutus

import androidx.lifecycle.ViewModel

class AboutUsViewModel : ViewModel() {
    val members: List<Member> = listOf(
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg", "Bui Minh Nhat", "s3878174", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg ", "Thieu Tran Tri Thuc", "s3870730", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg", "Lai Nghiep Tri", "s3799602", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg", "Phan Bao Kim Ngan", "s3914582", "Software Engineering")
    )
}