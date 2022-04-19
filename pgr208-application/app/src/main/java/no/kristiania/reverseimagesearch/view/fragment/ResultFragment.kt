package no.kristiania.reverseimagesearch.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import no.kristiania.reverseimagesearch.viewmodel.ResultViewModel
import no.kristiania.reverseimagesearch.databinding.FragmentResultBinding
import no.kristiania.reverseimagesearch.model.db.ImageSearchDb
import no.kristiania.reverseimagesearch.model.entity.RequestImage
import no.kristiania.reverseimagesearch.view.adapter.ResultItemAdapter
import no.kristiania.reverseimagesearch.viewmodel.ResultViewModelFactory
import no.kristiania.reverseimagesearch.viewmodel.api.FastNetworkingAPI
import no.kristiania.reverseimagesearch.viewmodel.utils.BitmapUtils
import no.kristiania.reverseimagesearch.viewmodel.utils.BitmapUtils.Companion.UriToBitmap

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Dette er "dataBinding/ViewBinding. Erstatter findById og er mer minne-effektiv
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root


        val api = context?.let { FastNetworkingAPI(it) }


        val application = requireNotNull(this.activity).application
        val db = ImageSearchDb.getInstance(application)
        val requestImageDao = db.requestImageDao
        val resultImageDao = db.resultImageDao
        val resultViewModelFactory = ResultViewModelFactory(requestImageDao, resultImageDao)
        val viewModel = ViewModelProvider(this, resultViewModelFactory)[ResultViewModel::class.java]
        viewModel.hostedImageServerUrl = ResultFragmentArgs.fromBundle(requireArguments()).responseUrl
        viewModel.requestImageLocalPath = ResultFragmentArgs.fromBundle(requireArguments()).requestImagePath
        Log.d("ResultFragment", viewModel.hostedImageServerUrl)


        if (api != null) {
            viewModel.getResultFromUrl(viewModel.hostedImageServerUrl, api)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        //binding.resultItemsList.
        // Til databinding med livedata
        val adapter = ResultItemAdapter()
        binding.resultItemsList.adapter = adapter
        // Observer endringer i view modellens liste av resultitems
        viewModel.resultImages.observe(viewLifecycleOwner, Observer {
            Log.i("ResultFragment", "Submitting list")
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.saveResultButton.setOnClickListener {
            viewModel.saveResult(requireContext(),adapter.selectedImagesForSave)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}