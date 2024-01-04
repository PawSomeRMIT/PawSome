package com.example.pawsome.data.signup

data class SignUpState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)