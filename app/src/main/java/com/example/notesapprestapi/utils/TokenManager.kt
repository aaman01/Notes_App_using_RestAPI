package com.example.notesapprestapi.utils

import android.content.Context
import com.example.notesapprestapi.utils.Const.PREFS_TOKEN
import com.example.notesapprestapi.utils.Const.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context){
    private val prefs= context.getSharedPreferences(PREFS_TOKEN, Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor=prefs.edit()
        //save token under const key USER)TOKEN
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }
    fun getToken():String?{
       //GET TOKEN WITH KEY USER_TOKEN  , if not present retuen null
        return  prefs.getString(USER_TOKEN,null)

    }
}