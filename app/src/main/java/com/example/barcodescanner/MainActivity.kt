package com.example.barcodescanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity(), MyInterface {
    private var results = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Main", "onCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        Log.d("Main", "Entered onActivityResult")
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Scanned -> " + result.contents, Toast.LENGTH_SHORT).show()
                results = result.contents
                Log.d("Main", "Scanned $results")
                setResults(results)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun setResults(results: String) {
        this.results = results
    }

    override fun getResults(): String {
        return results
    }

}