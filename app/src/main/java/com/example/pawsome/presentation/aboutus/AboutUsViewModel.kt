package com.example.pawsome.presentation.aboutus

import androidx.lifecycle.ViewModel
import com.example.pawsome.R

class AboutUsViewModel : ViewModel() {
    val members: List<Member> = listOf(
        Member(R.drawable.nhat, "Bui Minh Nhat", "s3878174", "Software Engineering"),
        Member(R.drawable.thuc, "Thieu Tran Tri Thuc", "s3870730", "Software Engineering"),
        Member(R.drawable.tri, "Lai Nghiep Tri", "s3799602", "Software Engineering"),
        Member(R.drawable.ngan, "Phan Bao Kim Ngan", "s3914582", "Software Engineering")
    )
}
