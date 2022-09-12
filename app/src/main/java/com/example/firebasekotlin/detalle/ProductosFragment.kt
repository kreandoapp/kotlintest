package com.example.firebasekotlin.detalle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasekotlin.R
import com.example.firebasekotlin.detalle.adapter.AdapterProductos
import com.example.firebasekotlin.detalle.viewmodel.ProductosViewModel
import kotlinx.android.synthetic.main.fragment_productos.*
import kotlinx.android.synthetic.main.fragment_productos.view.*


class ProductosFragment : Fragment() {

    private var viewmodel = ProductosViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        viewmodel = ViewModelProvider(requireActivity())[ProductosViewModel::class.java]


        view.rv_productos.layoutManager = LinearLayoutManager(requireActivity())

        viewmodel.listProductos.observe(requireActivity()) {
           rv_productos.adapter = AdapterProductos(it,viewmodel)
        }

        viewmodel.cart.observe(requireActivity(), Observer {
            Log.d("tag", it.size.toString())

            for (item in it){
                Log.d("tag", "nombre: ${item.nombre} - id: ${item.idprod} - cant: ${item.cantidad}")
            }

        })


        return view
    }


}