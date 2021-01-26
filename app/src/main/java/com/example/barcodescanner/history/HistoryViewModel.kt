package com.example.barcodescanner.history

import android.app.Application
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.database.LinkDatabaseDao
import com.example.barcodescanner.database.LinkSave
import com.example.barcodescanner.formatLinks
import kotlinx.coroutines.launch

class HistoryViewModel(
    val database: LinkDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    lateinit var links: List<LinkSave>
    var linkString = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    
    init {
        viewModelScope.launch {
                links = database.getAllLinks()
                linkString = formatLinks(links, application.resources)
                //TODO NOT UPDATING<----
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