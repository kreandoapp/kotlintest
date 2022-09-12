package com.example.firebasekotlin.detalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasekotlin.model.Locales
import com.example.firebasekotlin.model.Productos
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProductosViewModel : ViewModel(){
    val database = Firebase.database
    val productos = database.getReference("productos")

    val cart  = MutableLiveData<List<Productos>>()

    private val _listProductos  = MutableLiveData<List<Productos>>()
    val listProductos : LiveData<List<Productos>>
        get() = _listProductos

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        productos.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaProductos : List<Productos> = snapshot.children.map { data ->
                    data.getValue(Productos::class.java)!!
                }
                _listProductos.value = listaProductos
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}