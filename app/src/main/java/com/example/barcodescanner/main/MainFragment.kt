package com.example.barcodescanner.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.barcodescanner.MyInterface
import com.example.barcodescanner.R
import com.example.barcodescanner.databinding.FragmentMainBinding
import com.google.zxing.integration.android.IntentIntegrator


class MainFragment : Fragment() {

//    val application = requireNotNull(this.activity).application
//    val dataSource = LinkDatabase
//        .getInstance(application)
//        .linkDatabaseDao
    lateinit var btnBarcode: Button
    lateinit var txt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var mi: MyInterface? = null

    override fun onAttach(context: android.content.Context) {
        super.onAttach(requireContext())
        mi = context as MyInterface?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Main", "onCreateView")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        btnBarcode = binding.button
        txt = binding.txtContent

        btnBarcode.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this.activity)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()

        }
        //https://zxing.github.io/zxing/apidocs/com/google/zxing/integration/android/IntentIntegrator.html
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        txt.text = mi!!.getResults()
        val str = txt.text.toString()
//        insertIntoDB(str)
    }

//    fun insertIntoDB(URL: String){
//        HistoryViewModel(dataSource, application).insertNew(URL)
//    }
}