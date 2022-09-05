package com.example.firebasekotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasekotlin.model.Locales
import com.example.firebasekotlin.model.Productos
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FirebaseViewModel : ViewModel() {

    var auth = Firebase.auth
     val database = Firebase.database
     val locales = database.getReference("locales")

    val cart  = MutableLiveData<List<Productos>>()


    private val _listLocales  = MutableLiveData<List<Locales>>()
    val listLocales : LiveData<List<Locales>>
    get() = _listLocales

    private val _token  = MutableLiveData<String>()
    val token : LiveData<String>
        get() = _token

    init {
        cargarLocales()
    }

    @Suppress("UNCHECKED_CAST")
    fun removeProd (item : Productos){

        val lista = ArrayList<Productos>()

        for (item in cart.value!!){
            lista.add(Productos(item.id,item.nombre,item.cantidad,item.precio))
        }

        lista.remove(item)

        cart.value = lista
        Log.d("tag","Tamaño actual : ${cart.value!!.size.toString()}" )
        //Log.d("tag",lista.size.toString())
        /*lista.remove(item)
        cart.value = lista as List<Productos>*/

    }


    //Cargar desde firebase con viewmodel :)


    fun login(usuario: String,password:String){
        auth.signInWithEmailAndPassword("matias@mail.com","123456").addOnCompleteListener { task ->

            if(task.isSuccessful){

                val user = auth.currentUser
                _token.value = user!!.uid.toString()
            }else{
                Log.d("tag","Login Error!")
            }
        }
    }

    private fun cargarLocales() {

        locales.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaLocales : List<Locales> = snapshot.children.map { data ->
                    data.getValue(Locales::class.java)!!
                }
                _listLocales.value = listaLocales
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }






}