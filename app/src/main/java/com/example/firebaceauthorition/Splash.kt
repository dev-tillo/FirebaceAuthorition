package com.example.firebaceauthorition

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebaceauthorition.databinding.FragmentSplashBinding

class Splash : Fragment() {

    lateinit var fragment: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragment = FragmentSplashBinding.inflate(inflater, container, false)
        fragment.apply {
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splash_to_home2)
            }, 1000)
        }
        return fragment.root
    }
}