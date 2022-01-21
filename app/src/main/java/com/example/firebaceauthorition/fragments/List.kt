package com.example.firebaceauthorition.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebaceauthorition.R
import com.example.firebaceauthorition.adapters.HomeAdapter
import com.example.firebaceauthorition.databinding.FragmentListBinding

class List : Fragment() {

    lateinit var fragment: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentListBinding.inflate(inflater, container, false)
        fragment.apply {
            val string = arguments?.getString("key")

            number.text = string.toString()

            image.setOnClickListener {
                findNavController().popBackStack(R.id.home2, false)
            }
        }
        return fragment.root
    }
}