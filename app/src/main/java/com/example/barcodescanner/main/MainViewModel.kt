package com.example.barcodescanner.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.barcodescanner.database.LinkDatabaseDao

class MainViewModel(
        val database: LinkDatabaseDao,
        application: Application) : AndroidViewModel(application){

}