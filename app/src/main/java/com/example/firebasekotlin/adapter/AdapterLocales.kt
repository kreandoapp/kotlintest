package com.example.firebasekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.model.Productos

class AdapterLocales(private val lista : List<Productos>) : RecyclerView.Adapter<LocalesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_locales,parent,false)
        return LocalesViewHolder(view)

    }

    override fun onBindViewHolder(holder: LocalesViewHolder, position: Int) {

        val item = lista[position]

        holder.render(item)

    }

    override fun getItemCount(): Int = lista.size



}

class LocalesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val titulo = view.findViewById<TextView>(R.id.tv_titulo)
    val imagen = view.findViewById<ImageView>(R.id.iv_imagen)

    fun render (localmo : Productos){
      titulo.text = localmo.nombre

    }

}
