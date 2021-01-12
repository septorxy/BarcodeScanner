package com.example.barcodescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.barcodescanner.databinding.ActivityMainBinding
import com.example.barcodescanner.databinding.FragmentTitleBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}