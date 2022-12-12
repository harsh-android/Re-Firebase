package com.example.refirebase

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.refirebase.Model.UserModel
import com.example.refirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.Reference

class MainActivity : BaseActivity() {

    var logoUrl = "https://1757140519.rsc.cdn77.org/blog/wp-content/uploads/2020/08/google-gif-logo.gif"

    lateinit var auth:FirebaseAuth
    lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createDialog()
        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().getReference()

        Glide.with(applicationContext).load(logoUrl).into(binding.logo)

        binding.signUp.setOnClickListener {
            showDialog()
            var name = binding.name.text.toString()
            var surname = binding.surname.text.toString()
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            var confirmPassword = binding.confirmPass.text.toString()

            if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {

                if (email.contains("@gmail.com")) {
                    if (password.length >= 6) {
                        if (password.equals(confirmPassword)) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    binding.txtMsg.text = "User is Register Successfully"
                                    binding.txtMsg.setTextColor(Color.GREEN)

//                                    TODO Data Add in Realtime Database
                                    var key = reference.root.push().key
                                    var model = UserModel(key!!,name, surname, email, password)
                                    reference.root.child("User").child(key).setValue(model)
                                    closeDialog()
                                    startActivity(Intent(applicationContext,UserListActivity::class.java))

                                }.addOnFailureListener { error ->
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
            } else {
                Toast.makeText(applicationContext, "All Field are Required", Toast.LENGTH_SHORT).show()
            }
        }
    }


}