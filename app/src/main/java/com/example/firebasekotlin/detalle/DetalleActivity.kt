package com.example.firebasekotlin.detalle

import android.os.Bundle
import androidx.activity.viewModels
import com.example.firebasekotlin.BaseOneFragmentActivity
import com.example.firebasekotlin.detalle.viewmodel.ProductosViewModel
import com.example.firebasekotlin.viewmodel.FirebaseViewModel

class DetalleActivity : BaseOneFragmentActivity() {

    private val viewmodel : ProductosViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        addFragment(ProductosFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle = intent.extras

        val name = bundle?.getString("nombre")
        val id = bundle?.getString("id")
        setToolbarTitle(name!! + "-"+ id )





    }
}