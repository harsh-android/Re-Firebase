package com.example.refirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.refirebase.Model.UserModel

class UserAdapter(arrayList: ArrayList<UserModel>) : Adapter<UserAdapter.UserHolder>() {
    var list = arrayList
    lateinit var context: Context
    class UserHolder(itemView: View) : ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var email = itemView.findViewById<TextView>(R.id.email)
        var profile = itemView.findViewById<ImageView>(R.id.profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        context = parent.context
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.name.text = list.get(position).name+" "+list.get(position).surname
        holder.email.text = list.get(position).email
        Glide.with(context).load(list.get(position).profile).into(holder.profile)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}