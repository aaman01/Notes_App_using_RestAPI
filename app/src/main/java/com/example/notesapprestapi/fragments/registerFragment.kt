package com.example.notesapprestapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notesapprestapi.AuthViewModel.AuthViewModel
import com.example.notesapprestapi.R
import com.example.notesapprestapi.databinding.FragmentRegisterBinding
import com.example.notesapprestapi.models.UserRequest
import com.example.notesapprestapi.utils.NetworkResult
import com.example.notesapprestapi.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class registerFragment : Fragment() {
  private var _binding:FragmentRegisterBinding?=null
   private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentRegisterBinding.inflate(inflater,container,false)

        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            val validateuser=validateuserinput()
            if (validateuser.first){

                authViewModel.registeruser(getuservalue())
            }else
            {
                binding.txtError.text=validateuser.second
            }


        }
        binding.btnLogin.setOnClickListener {

          findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

         bindObserver()

    }
    private fun getuservalue():UserRequest{
        val email=binding.txtEmail.text.toString().trim()
        val password=binding.txtPassword.text.toString().trim()
        val username=binding.txtUsername.toString().trim()

        return  UserRequest(email,password,username)
    }
   private fun validateuserinput (): Pair<Boolean, String> {
      val getuservalue=getuservalue()

      return authViewModel.validcredentials(getuservalue.username, getuservalue.password,  getuservalue.email,false)
   }
    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){

                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}