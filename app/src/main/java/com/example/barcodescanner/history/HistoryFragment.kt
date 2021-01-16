package com.example.barcodescanner.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.barcodescanner.R
import com.example.barcodescanner.database.LinkDatabase
import com.example.barcodescanner.databinding.FragmentHistoryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = LinkDatabase
                .getInstance(application)
                .linkDatabaseDao

        val viewModelFactory = HistoryViewModelFactory(dataSource, application)

        val historyViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(HistoryViewModel::class.java)
        binding.historyViewModel = historyViewModel
        return binding.root
    }




}