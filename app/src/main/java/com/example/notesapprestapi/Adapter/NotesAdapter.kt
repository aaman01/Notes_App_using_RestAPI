package com.example.notesapprestapi.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapprestapi.databinding.NoteItemBinding
import com.example.notesapprestapi.models.NoteResponse
import kotlin.reflect.KFunction1

class NotesAdapter(private val onnoteclick:(NoteResponse) -> Unit) :ListAdapter<NoteResponse, NotesAdapter.NoteViewHolder>(ComparatorDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val binding=NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

  inner class NoteViewHolder(private val binding: NoteItemBinding):RecyclerView.ViewHolder(binding.root){
      fun bind(note:NoteResponse){
          binding.title.text=note.Title
          binding.desc.text=note.Description
          binding.root.setOnClickListener {
        onnoteclick(note)
          }
      }
  }


    class ComparatorDiffUtil:DiffUtil.ItemCallback<NoteResponse>(){
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
           return oldItem._id==newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

    }
}