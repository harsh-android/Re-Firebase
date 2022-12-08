package com.example.refirebase

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.refirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var logoUrl = "https://1757140519.rsc.cdn77.org/blog/wp-content/uploads/2020/08/google-gif-logo.gif"

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        Glide.with(applicationContext).load(logoUrl).into(binding.logo)

        binding.signUp.setOnClickListener {

            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            var confirmPassword = binding.confirmPass.text.toString()

            if (email.contains("@gmail.com")) {
                if (password.length >= 6) {
                    if (password.equals(confirmPassword)) {
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                            binding.txtMsg.text = "User is Register Successfully"
                            binding.txtMsg.setTextColor(Color.GREEN)
                        }.addOnFailureListener { error->
                            binding.txtMsg.text = "User Register is Failed Please Retry Again!"
                            binding.txtMsg.setTextColor(Color.RED)
                        }
                    } else {
                        binding.confirmPass.error = "Enter Valid Password"
                    }
                } else {
                    binding.password.error = "Enter Long and Strong Password"
                }
            } else {
                binding.email.error = "Enter Valid Email"
            }
        }

    }
}