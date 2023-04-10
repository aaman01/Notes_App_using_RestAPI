package com.example.notesapprestapi.models

data class NoteResponse(
    val Description: String,
    val Title: String,
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val userID: String
)