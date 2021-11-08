package com.example.cvsflicker.base

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context : Context){
    private var PREF_SEARCHED_LIST = "prefSearchedList"
    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_SEARCHED_LIST,Context.MODE_PRIVATE)

    fun getList() : MutableSet<String>? {
        return preferences.getStringSet(PREF_SEARCHED_LIST, null)
    }

    fun setList(searchedList: MutableSet<String>){

        val editor : SharedPreferences.Editor = preferences.edit()
        editor.putStringSet("prefSearchedList",searchedList)
        editor.apply()
    }
}