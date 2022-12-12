package com.example.refirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.refirebase.Model.UserModel

class UserAdapter(arrayList: ArrayList<UserModel>) : Adapter<UserAdapter.UserHolder>() {
    var list = arrayList
    class UserHolder(itemView: View) : ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var email = itemView.findViewById<TextView>(R.id.email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.name.text = list.get(position).name+" "+list.get(position).surname
        holder.email.text = list.get(position).email
    }

    override fun getItemCount(): Int {
        return list.size
    }

}