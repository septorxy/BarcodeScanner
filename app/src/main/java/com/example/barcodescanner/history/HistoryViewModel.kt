package com.example.barcodescanner.history

import android.app.Application
import android.text.Spanned
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.database.LinkDatabaseDao
import com.example.barcodescanner.database.LinkSave
import com.example.barcodescanner.formatLinks
import kotlinx.coroutines.launch

class HistoryViewModel(
    val database: LinkDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    lateinit var links: LiveData<List<LinkSave>>
    lateinit var linkString: LiveData<Spanned>

    init{
        viewModelScope.launch {
            links = database.getAllLinks()

            Log.d("Main","Entered init ")

            linkString = Transformations.map(links) { links ->
                formatLinks(links, application.resources)
            }
        }
    }


    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        database.clear()
    }
}