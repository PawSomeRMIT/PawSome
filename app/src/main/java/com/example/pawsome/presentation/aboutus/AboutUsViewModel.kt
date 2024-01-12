package com.example.pawsome.presentation.aboutus

import androidx.lifecycle.ViewModel

class AboutUsViewModel : ViewModel() {
    val members: List<Member> = listOf(
        Member("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Ffree-photos-vectors%2Favatar&psig=AOvVaw1vCjcpW34D1o8IniH98Cyt&ust=1705141514869000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJjqs6XR14MDFQAAAAAdAAAAABAD", "Bui Minh Nhat", "s3878174", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg ", "Thieu Tran Tri Thuc", "s3870730", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg", "Lai Nghiep Tri", "s3799602", "Software Engineering"),
        Member("https://i.postimg.cc/7hjg2wZm/836.jpg", "Phan Bao Kim Ngan", "s3914582", "Software Engineering")
       /*
        Member(R.drawable.nhat, "Bui Minh Nhat", "s3878174", "Software Engineering"),
        Member(R.drawable.thuc, "Thieu Tran Tri Thuc", "s3870730", "Software Engineering"),
        Member(R.drawable.tri, "Lai Nghiep Tri", "s3799602", "Software Engineering"),
        Member(R.drawable.ngan, "Phan Bao Kim Ngan", "s3914582", "Software Engineering")
    */
    )
}
