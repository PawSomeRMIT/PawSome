package com.example.pawsome.data.repository

import com.example.pawsome.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}