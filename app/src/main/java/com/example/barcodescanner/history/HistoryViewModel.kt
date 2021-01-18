package com.example.barcodescanner.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    private val links = database.getAllLinks()

    val LinkString = Transformations.map(links) { links ->
        formatLinks(links, application.resources)
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        database.clear()
    }

    fun insertNew(url: String) {
        viewModelScope.launch {
            val linkSave = LinkSave()
            linkSave.linkURL
            insert(linkSave)
        }
    }

    private suspend fun insert(linkSave: LinkSave) {
        database.insert(linkSave)
    }


}