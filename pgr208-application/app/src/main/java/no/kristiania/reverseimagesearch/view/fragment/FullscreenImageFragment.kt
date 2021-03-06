package no.kristiania.reverseimagesearch.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import no.kristiania.reverseimagesearch.R

class FullscreenImageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("FULLSCREEN_CREATED", "Fullscreen created")

        return inflater.inflate(R.layout.fullscreen_image_fragment, container, false)
    }

}