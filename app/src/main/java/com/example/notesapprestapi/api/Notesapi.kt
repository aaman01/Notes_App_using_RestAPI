package com.example.notesapprestapi.api

import com.example.notesapprestapi.models.NoteRequest
import com.example.notesapprestapi.models.NoteResponse
import com.example.notesapprestapi.models.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface Notesapi {

    @GET("/note")
    suspend fun getnotes(): Response<List<NoteResponse>>

    @POST("/note")
    suspend fun createnotes(@Body noteRequest: NoteRequest):Response<NoteResponse>

    @PUT("/note/{noteID}")
    suspend fun updatenotes(@Path("noteID") noteID:String, @Body noteRequest: NoteRequest):Response<NoteResponse>

    @DELETE("/note/{noteID}")
    suspend fun deletenotes(@Path("noteID")noteID: String):Response<NoteResponse>
}