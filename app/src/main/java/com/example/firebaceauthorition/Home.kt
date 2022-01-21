package com.example.firebaceauthorition

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaceauthorition.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.*
import java.util.concurrent.TimeUnit


class Home : Fragment() {

    lateinit var fragment: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentHomeBinding.inflate(inflater, container, false)
        fragment.apply {
            google.setOnClickListener {
                findNavController().navigate(R.id.action_home2_to_googleHome)
            }
            cardsave.setOnClickListener {
                val number = number.text.toString()
                if (number.isNotEmpty() || number.length < 13) {
                    val bundle = Bundle()
                    bundle.putString("key", number)
                    findNavController().navigate(R.id.action_home2_to_number2, bundle)
                    Toast.makeText(requireContext(), "Your sms sended", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Raqamingizni kiriting", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return fragment.root
    }
}