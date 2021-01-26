package com.example.barcodescanner.history

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.barcodescanner.R
import com.example.barcodescanner.database.LinkDatabase
import com.example.barcodescanner.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    lateinit var txt: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHistoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = LinkDatabase
            .getInstance(application)
            .linkDatabaseDao
        txt = binding.textview;
        txt.movementMethod = LinkMovementMethod.getInstance()


        val viewModelFactory = HistoryViewModelFactory(dataSource, application)

        val historyViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.historyViewModel = historyViewModel
        historyViewModel.linkString.observe(viewLifecycleOwner, Observer { newLinkString ->
            binding.textview.text = newLinkString
        })

        return binding.root
    }


}