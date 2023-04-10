package com.example.notesapprestapi.models

data class UserResponse(
    val token: String,
    val user: User
)