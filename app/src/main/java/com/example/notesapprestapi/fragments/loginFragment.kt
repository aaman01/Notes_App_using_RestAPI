package com.example.notesapprestapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapprestapi.AuthViewModel.AuthViewModel
import com.example.notesapprestapi.R
import com.example.notesapprestapi.databinding.FragmentLoginBinding
import com.example.notesapprestapi.models.UserRequest
import com.example.notesapprestapi.utils.NetworkResult
import com.example.notesapprestapi.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class loginFragment : Fragment() {
   private var _binding:FragmentLoginBinding?=null
    private  val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val validateuser=validateuserinput()
            if (validateuser.first) {
                authViewModel.loginuser(getuservalue())
            }else
            {
                binding.txtError.text=validateuser.second
            }

        }
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObserver()
    }


    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){

                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error->{
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading ->{
                    binding.progressBar.isVisible=true

                }
            }
        })
    }

    private fun getuservalue(): UserRequest {
        val email=binding.txtEmail.text.toString().trim()
        val password=binding.txtPassword.text.toString().trim()
        return  UserRequest(email,password,"")
    }

    private fun validateuserinput (): Pair<Boolean, String> {
        val getuservalue=getuservalue()

        return authViewModel.validcredentials(getuservalue.username, getuservalue.password,  getuservalue.email,true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    }
