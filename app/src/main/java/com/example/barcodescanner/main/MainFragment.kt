package com.example.barcodescanner.main

import android.app.Application
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.barcodescanner.MyInterface
import com.example.barcodescanner.R
import com.example.barcodescanner.database.LinkDatabase
import com.example.barcodescanner.database.LinkDatabaseDao
import com.example.barcodescanner.databinding.FragmentMainBinding
import com.google.zxing.integration.android.IntentIntegrator


class MainFragment : Fragment() {
    lateinit var btnBarcode: Button
    lateinit var txt: TextView

    private var mi: MyInterface? = null
    lateinit var application: Application
    lateinit var dataSource: LinkDatabaseDao

    override fun onAttach(context: android.content.Context) {
        super.onAttach(requireContext())
        mi = context as MyInterface?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Main", "onCreateView")
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        application = requireNotNull(this.activity).application
        dataSource = LinkDatabase
            .getInstance(application)
            .linkDatabaseDao

        btnBarcode = binding.button
        txt = binding.txtContent

        txt.movementMethod = LinkMovementMethod.getInstance()

        btnBarcode.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this.activity)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setCameraId(0)
            intentIntegrator.setBarcodeImageEnabled(false)
            intentIntegrator.initiateScan()

        }
        //https://zxing.github.io/zxing/apidocs/com/google/zxing/integration/android/IntentIntegrator.html
        setHasOptionsMenu(true)

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val mainViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel
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
        val text = "<a href='${mi!!.getResults()}'>${mi!!.getResults()}</a>"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txt.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            txt.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        val str = mi!!.getResults()
        MainViewModel(dataSource, application).insertNew(str)
        mi!!.setResults("")
    }


}