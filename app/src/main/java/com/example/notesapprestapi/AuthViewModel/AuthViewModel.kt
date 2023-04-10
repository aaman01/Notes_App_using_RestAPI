package com.example.notesapprestapi.AuthViewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapprestapi.models.UserRequest
import com.example.notesapprestapi.models.UserResponse
import com.example.notesapprestapi.repository.UserRepository
import com.example.notesapprestapi.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
  val userResponseLiveData:LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData
    fun registeruser(userRequest: UserRequest){
       viewModelScope.launch {
           userRepository.registeruser(userRequest)
       }

    }


    fun loginuser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginuser(userRequest)
        }

    }

    fun validcredentials(username:String, password:String,email:String,islogin:Boolean):Pair<Boolean,String>{
        var Result=Pair(true,"")
        if( (!islogin && TextUtils.isEmpty(username) )||TextUtils.isEmpty(password)|| TextUtils.isEmpty(email)){
             Result= Pair(false,"Please Provide valid credentials ")
        }
     else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
         Result=Pair(false,"Please Provide Valid  Email")
        }
        else if(password.length<6){
            Result=Pair(false,"Password length should be greater than 5")

        }
        return Result
    }
}