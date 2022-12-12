package com.example.refirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refirebase.Model.UserModel
import com.example.refirebase.databinding.ActivityUserListBinding
import com.google.firebase.database.*

class UserListActivity : AppCompatActivity() {

    lateinit var reference:DatabaseReference
    var arrayList = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reference = FirebaseDatabase.getInstance().getReference()

//        reference.root.child("User").removeValue()

        reference.root.child("User").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    var model = snap.getValue(UserModel::class.java)
                    arrayList.add(model!!)
                }

                binding.rcvUserList.layoutManager = LinearLayoutManager(applicationContext)
                binding.rcvUserList.adapter = UserAdapter(arrayList)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



    }

}