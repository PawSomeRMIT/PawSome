package com.example.pawsome.presentation.aboutus

import com.example.pawsome.R
import com.example.pawsome.model.Member

class AboutUsData {
    val members: List<Member> = listOf(
        Member(R.drawable.nhat, "Bui Minh Nhat", "s3878174", "Software Engineering"),
        Member(R.drawable.thuc, "Thieu Tran Tri Thuc", "s3870730", "Software Engineering"),
        Member(R.drawable.tri, "Lai Nghiep Tri", "s3799602", "Software Engineering"),
        Member(R.drawable.ngan, "Phan Bao Kim Ngan", "s3914582", "Software Engineering")
    )

    val technology: List<Member> = listOf(
        Member(R.drawable.firebase, "Firebase", "", ""),
        Member(R.drawable.kotlin, "Kotlin", "", ""),
        Member(R.drawable.jetpack, "Jetpack Compose", "", ""),
        Member(R.drawable.stripe, "Stripe", "", ""),
        Member(R.drawable.vnpt, "VNPT e-KYC", "", "")
    )
}
