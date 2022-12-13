package com.example.refirebase

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.refirebase.Model.UserModel
import soup.neumorphism.NeumorphCardView
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(arrayList: ArrayList<UserModel>) : Adapter<UserAdapter.UserHolder>() {
    var list = arrayList
    lateinit var context: Context
    class UserHolder(itemView: View) : ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var email = itemView.findViewById<TextView>(R.id.email)
        var profile = itemView.findViewById<ImageView>(R.id.profile)
        var cards = itemView.findViewById<NeumorphCardView>(R.id.cards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        context = parent.context
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.name.text = list.get(position).name+" "+list.get(position).surname
        holder.email.text = list.get(position).email
        val rnd = Random()
        var baseColor = Color.WHITE;

        var baseRed = Color.red(baseColor);
        var baseGreen = Color.green(baseColor);
        var baseBlue = Color.blue(baseColor);
        val color: Int = Color.argb(255, baseRed+rnd.nextInt(256)/2,
            baseGreen+rnd.nextInt(256)/2, baseBlue+rnd.nextInt(256)/2)
        holder.cards.setShadowColorDark(color)
        Glide.with(context).load(list.get(position).profile).into(holder.profile)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}