package com.example.notesapprestapi.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapprestapi.NoteViewModel
import com.example.notesapprestapi.R
import com.example.notesapprestapi.databinding.FragmentNoteBinding
import com.example.notesapprestapi.models.NoteRequest
import com.example.notesapprestapi.models.NoteResponse
import com.example.notesapprestapi.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class noteFragment : Fragment() {
  private var _binding:FragmentNoteBinding?=null
  private  val binding get() = _binding!!
    private var note:NoteResponse?=null
    private val noteViewModel by  viewModels<NoteViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentNoteBinding.inflate(layoutInflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedinstance()
        bindhandlers()
        bindobservers()
    }

    private fun bindobservers() {
        noteViewModel.statuslivedata.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })

    }

    private fun bindhandlers() {
        binding.btnDelete.setOnClickListener {
            Log.d("ok","BTN_WORKING")
            note?.let {
                Log.d("ok","BTN_WORKING2")
                noteViewModel.deleteNotes(it._id)
                Log.d("ok","BTN_WORKING3")
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title=binding.txtTitle.text.toString()
            val des=binding.txtDescription.text.toString()
            val noteRequest=NoteRequest(des,title)
            if (note==null){
                noteViewModel.createNotes(noteRequest)

            }else{

                noteViewModel.updateNotes(note!!._id,noteRequest)
            }
        }
    }

    private fun savedinstance() {
        //data came from main fragment is received using arguments and putting the key
       val jsonObject=arguments?.getString("note")
        if (jsonObject!=null){
            //edit case
            //using Gson we create object of class Noteresponse from string jsonObject
            note=Gson().fromJson(jsonObject,NoteResponse::class.java )
            note?.let {
                binding.txtTitle.setText(it.Title)
                binding.txtDescription.setText(it.Description)


            }
        }
        else{
            //add case as no previous note is found
            binding.addEditText.text="Add Note"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}