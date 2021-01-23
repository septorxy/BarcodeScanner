package com.example.barcodescanner.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.database.LinkDatabaseDao
import com.example.barcodescanner.database.LinkSave
import kotlinx.coroutines.launch

class MainViewModel(
    val database: LinkDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    fun insertNew(url: String) {
        viewModelScope.launch {
            val linkSave = LinkSave()
            if (url != "") {
                linkSave.linkURL = url
                insert(linkSave)
            }
            Log.d("Main", "insertNew Entered")
        }
    }

    private suspend fun insert(linkSave: LinkSave) {
        database.insert(linkSave)
        Log.d("Main", linkSave.linkURL)
    }

}