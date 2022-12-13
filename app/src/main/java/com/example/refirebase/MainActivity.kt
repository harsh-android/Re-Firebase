package com.example.refirebase

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.refirebase.Model.UserModel
import com.example.refirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : BaseActivity() {

    var logoUrl =
        "https://1757140519.rsc.cdn77.org/blog/wp-content/uploads/2020/08/google-gif-logo.gif"

    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var galleryImage: Uri
    lateinit var storageRef: StorageReference
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createDialog()
        auth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().getReference()
        reference = FirebaseDatabase.getInstance().getReference()

        Glide.with(applicationContext).load(logoUrl).into(binding.logo)

        binding.logo.setOnClickListener {
            var intent = Intent(ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 20)
        }

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

                                    UploadData(name,surname,email,password)

                                }.addOnFailureListener { error ->
                                    binding.txtMsg.text =
                                        "User Register is Failed Please Retry Again!"
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
                Toast.makeText(applicationContext, "All Field are Required", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun UploadData(name: String, surname: String, email: String, password: String) {
//        TODO Data Add in Realtime Database

        val ref = storageRef.child("images/" + galleryImage.lastPathSegment)
        var uploadTask = ref.putFile(galleryImage)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                var key = reference.root.push().key
                var model = UserModel(key!!, name, surname, email, password,downloadUri.toString())
                reference.root.child("User").child(key).setValue(model)
                closeDialog()
                startActivity(Intent(applicationContext, UserListActivity::class.java))
            } else {
                // Handle failures
                // ...
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 20) {
                galleryImage = data?.data!!
                binding.logo.setImageURI(galleryImage)
            }
        }

    }

}