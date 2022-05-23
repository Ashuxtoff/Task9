package com.example.task9new.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task9new.R
import com.example.task9new.presentation.MainActivity

class AppInformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_app_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        activity.supportActionBar?.setTitle(R.string.aboutAppToolbarTitle)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    companion object {
        fun newInstance() = AppInformationFragment()
    }
}