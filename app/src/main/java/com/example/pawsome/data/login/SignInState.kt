package com.example.pawsome.data.login

data class SignInState (
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)