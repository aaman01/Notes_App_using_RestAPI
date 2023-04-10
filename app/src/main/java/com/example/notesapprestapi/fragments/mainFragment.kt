package com.example.notesapprestapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapprestapi.Adapter.NotesAdapter
import com.example.notesapprestapi.NoteViewModel
import com.example.notesapprestapi.R
import com.example.notesapprestapi.databinding.FragmentMainBinding
import com.example.notesapprestapi.models.NoteResponse
import com.example.notesapprestapi.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class mainFragment : Fragment() {
 private var _binding:FragmentMainBinding?=null
     private  val binding get() = _binding!!
    private val noteViewModel by viewModels<NoteViewModel>()
private lateinit var adapter:NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         _binding=FragmentMainBinding.inflate(inflater,container,false)
          adapter=NotesAdapter(::noteclick)

            return binding.root
    }

    private fun noteclick(noteresponse: NoteResponse) {
        val bundle=Bundle()
        //key is note , and used Gson to convert noteresponse to string
        bundle.putString("note",Gson().toJson(noteresponse))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundle)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindobserver()
        noteViewModel.getNotes()
        binding.noteList.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
         binding.noteList.adapter=adapter
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }
        bindobserver()
    }

    private fun bindobserver() {
      noteViewModel.notelivedata.observe(viewLifecycleOwner, Observer {
          binding.progressBar.isVisible=false
          when(it){
              is NetworkResult.Error -> {
             Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
              }
              is NetworkResult.Loading -> {
                  binding.progressBar.isVisible=true
              }
              is NetworkResult.Success -> {
                 adapter.submitList(it.data)
              }
          }
      })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}