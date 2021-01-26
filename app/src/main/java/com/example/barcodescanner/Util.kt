package com.example.barcodescanner

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import com.example.barcodescanner.database.LinkSave
import java.text.SimpleDateFormat

fun formatLinks(links: List<LinkSave>, resources: Resources): Spanned {
    val sb = StringBuilder()
    Log.d("Main","formatLinks")
    sb.apply {
        append(resources.getString(R.string.title))
        links.forEach {
            append("<br>")
            append(resources.getString(R.string.time))
            append("\t${convertLongToDateString(it.timeScanned)}<br>")
            append("\t<a href='${it.linkURL}'>${it.linkURL}</a><br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE dd-MM-yyyy' Time: 'HH:mm").format(systemTime).toString()
}