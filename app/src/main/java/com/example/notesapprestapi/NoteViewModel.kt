package com.example.notesapprestapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapprestapi.models.NoteRequest
import com.example.notesapprestapi.repository.Noterepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NoteViewModel @Inject constructor(private  val noterepository: Noterepository):ViewModel() {

     val notelivedata get() = noterepository.noteslivedata
      val statuslivedata get() = noterepository.statuslivedata

    fun getNotes(){
        viewModelScope.launch {
            noterepository.getNotes()
        }
    }

    fun createNotes(noteRequest: NoteRequest){
        viewModelScope.launch {
            noterepository.createNotes(noteRequest)
        }
    }

    fun deleteNotes(noteID:String){
        viewModelScope.launch {
            noterepository.deleteNotes(noteID)
        }
    }

    fun updateNotes(noteID: String, noteRequest: NoteRequest){
        viewModelScope.launch {
            noterepository.updateNotes(noteID,noteRequest)
        }
    }
}