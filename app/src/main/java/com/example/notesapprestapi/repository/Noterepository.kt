package com.example.notesapprestapi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapprestapi.api.Notesapi
import com.example.notesapprestapi.models.NoteRequest
import com.example.notesapprestapi.models.NoteResponse
import com.example.notesapprestapi.models.UserResponse
import com.example.notesapprestapi.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class Noterepository @Inject constructor(private  val notesapi: Notesapi) {
     private  val _noteslivedata= MutableLiveData<NetworkResult<List<NoteResponse>>>()
     val noteslivedata :LiveData<NetworkResult<List<NoteResponse>>>
          get() =_noteslivedata

     private val _statuslivedata= MutableLiveData<NetworkResult<String>>()
     val statuslivedata:LiveData<NetworkResult<String>>
     get() = _statuslivedata

     suspend fun getNotes(){
          _noteslivedata.postValue(NetworkResult.Loading())
          val response=notesapi.getnotes()
          getnotehandleresponse(response)
     }
     suspend fun createNotes(noteRequest: NoteRequest){
          _statuslivedata.postValue(NetworkResult.Loading())
          val response = notesapi.createnotes(noteRequest)
          handleResponse(response,"Note created")
     }


     suspend fun deleteNotes(noteID:String ){
          _statuslivedata.postValue(NetworkResult.Loading())
          val response=notesapi.deletenotes(noteID)
          handleResponse(response,"Note Deleted")
     }

     suspend fun updateNotes(noteID:String , noteRequest: NoteRequest){
          _statuslivedata.postValue(NetworkResult.Loading())
          val response=notesapi.updatenotes(noteID,noteRequest)
          handleResponse(response,"Note Updated")
     }


     private fun handleResponse(response: Response<NoteResponse>,message:String) {
          if (response.isSuccessful && response.body() != null) {
               _statuslivedata.postValue(NetworkResult.Success(message))
          } else {
               _statuslivedata.postValue(NetworkResult.Error("Something went wrong"))
          }
     }



     private fun getnotehandleresponse(response: Response<List<NoteResponse>>) {
          if (response.isSuccessful && response.body() != null) {
               _noteslivedata.postValue(NetworkResult.Success(response.body()!!))

          } else if (response.errorBody() != null) {
               val errorobj = JSONObject(response.errorBody()!!.charStream().readText())
               _noteslivedata.postValue(NetworkResult.Error(errorobj.getString("message")))
          } else {
               _noteslivedata.postValue(NetworkResult.Error("Something when wrong"))
          }
     }


}