package com.example.firebasekotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.firebasekotlin.databinding.ActivityMainBinding
import com.example.firebasekotlin.model.Productos
import com.example.firebasekotlin.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs
import java.util.*


class MainActivity : AppCompatActivity() {


    private val viewmodel : FirebaseViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.listLocales.observe(this) {
            for (item in it) {
                Log.d("tag", "${item.nombre} - ${item.direccion}")
            }
        }

      /*  binding.btnReseteo.setOnClickListener {
            val list = mutableListOf<Productos>()
            viewmodel.cart.value = list
        }*/
        binding.btnEdit.setOnClickListener {

            val id = et_pass.text.toString()
            val texto = et_user.text.toString()
            val lista = ArrayList<Productos>()

            for (item in viewmodel.cart.value!!){
                if(item.id == id){
                    lista.add(Productos(item.id,texto,item.cantidad,item.precio))
                }else{
                    lista.add(Productos(item.id,item.nombre,item.cantidad,item.precio))
                }

                viewmodel.cart.value = lista

            }
        }
        binding.btnReset.setOnClickListener {
            viewmodel.cart.value = emptyList()
            binding.tvTotal.text = "0"
        }
        binding.btnRemove.setOnClickListener {

            val id = et_pass.text.toString()

            val lista = ArrayList<Productos>()

            for (item in viewmodel.cart.value!!){
                if(item.id != id){
                    lista.add(Productos(item.id,item.nombre,item.cantidad,item.precio))
                }

            }
            viewmodel.cart.value = lista
            Log.d("tag","Tamaño actual : ${lista.size.toString()}" )
        }
        binding.btnLogin.setOnClickListener {
            if(viewmodel.cart.value == null){
                val list = mutableListOf<Productos>()
                val random = kotlin.math.abs((0..999999999999).random())
                list.add(Productos(random.toString(),"Burger","1",400))
                viewmodel.cart.value = list
            }else{
                val random = kotlin.math.abs((0..999999999999).random())
                val list = mutableListOf<Productos>()
                list.add(Productos(random.toString(),"Burger2","1",400))
                viewmodel.cart.value = viewmodel.cart.value!! + list
            }


        }

        viewmodel.cart.observe(this){
            tv_count.text = it.size.toString()

            if(it != null){
                var total = 0
                for (cart in it){
                    //Log.d("tag",cart.id)
                    total += cart.precio
                    Log.d("productos", "Id - ${cart.id} - Nombre: ${cart.nombre}- precio: ${cart.precio} - cantidad : ${cart.cantidad}")
                    //Log.d("tag",total.toString()) //total
                    binding.tvTotal.text = total.toString()
                }

            }


        }

        //viewmodel.login("matias@mail.com","123456")


        viewmodel.token.observe(this){
            Log.d("tag",it)

        }








    }
}