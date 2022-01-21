package com.example.firebaceauthorition.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.firebaceauthorition.R
import com.example.firebaceauthorition.databinding.FragmentNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import android.os.CountDownTimer
import androidx.navigation.fragment.findNavController

class Number : Fragment() {

    lateinit var frament: FragmentNumberBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "SecondActivity"
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        frament = FragmentNumberBinding.inflate(inflater, container, false)
        val arg = arguments?.getString("key")

        frament.apply {
            num.text = arg.toString()
            auth = FirebaseAuth.getInstance()

            time.text = startResendTimer(60).toString()

            sendVerificationCode(num.toString())

            number.addTextChangedListener {
                if (it?.length == 6) {
                    verifyCode(it.toString())
                    var bundle = Bundle()
                    bundle.putString("key", arg)
                    findNavController().navigate(R.id.action_number2_to_list, bundle)
                }
            }

            refresh.setOnClickListener {
                val phoneNumber = num.text.toString()
                reSendVerificationCode(phoneNumber)
            }
        }
        return frament.root
    }

    override fun onResume() {
        super.onResume()
        sendVerificationCode(arguments?.getString("key").toString())
    }

    private fun reSendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setForceResendingToken(resendToken)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyCode(code: String) {
        if (code.length == 6) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
            } else if (e is FirebaseTooManyRequestsException) {
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
//                    val user = task.result?.user
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startResendTimer(seconds: Int) {
        frament.time.visibility = View.VISIBLE
        frament.refresh.isEnabled = false
        object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var secondsString = (millisUntilFinished / 1000).toString()
                if (millisUntilFinished < 600) {
                    secondsString = "0$secondsString"
                    frament.refresh.setOnClickListener {
                        Toast.makeText(requireContext(), "Daqiqa tugasin", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                frament.time.text = " 0:$secondsString"
            }

            override fun onFinish() {
                frament.refresh.isEnabled = true
                frament.time.visibility = View.GONE
                frament.refresh.setOnClickListener {
                    frament.time.visibility = View.VISIBLE
                    startResendTimer(60).toString()
                    val phoneNumber = arguments?.getString("key")
                    reSendVerificationCode(phoneNumber.toString())
                }
            }
        }.start()
    }
}