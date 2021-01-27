package com.example.barcodescanner.history

import android.app.Application
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _linkString = MutableLiveData<Spanned>()

    //var linkString: LiveData<String>

    val linkString: LiveData<Spanned>
        get() = _linkString

    init {
        _linkString.value = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml("", Html.FROM_HTML_MODE_LEGACY)
        } else {
            HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        viewModelScope.launch {
                links = database.getAllLinks()
                _linkString.value = formatLinks(links, application.resources)

        }

    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            _linkString.value = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml("<h3>Your past scanned links can be found here</h3>", Html.FROM_HTML_MODE_LEGACY)
            } else {
                HtmlCompat.fromHtml("<h3>Your past scanned links can be found here</h3>", HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    private suspend fun clear() {
        database.clear()

    }

}