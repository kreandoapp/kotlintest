package com.example.firebasekotlin.detalle.adapter

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.MainActivity
import com.example.firebasekotlin.R
import com.example.firebasekotlin.detalle.viewmodel.ProductosViewModel
import com.example.firebasekotlin.model.Productos
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class AdapterProductos(private val lista: List<Productos>, val viewmodel: ProductosViewModel) : RecyclerView.Adapter<ProductosViewHolder>(){

     var count : Int = 1
     var anular : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productos,parent,false)
        return ProductosViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {

        val item = lista[position]

        holder.nombre.text = item.nombre

        holder.btnadd.setOnClickListener {
            count = 1
            Log.d("tag",count.toString())
            Log.d("tag",anular.toString())

            holder.btnadd.visibility = View.GONE
            holder.contenedor.visibility = View.VISIBLE
            Log.d("tag","EN delay")
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d("tag","Sali del delay")
                if (count == 1 && anular==false) {
                    holder.contenedor.visibility = View.GONE
                    holder.view_cant.visibility = View.VISIBLE
                    //anular = false

                    crear(item)

                }else{
                    Log.d("tag","OTRO")

                }


            }, 3000)
        }

        holder.sumar.setOnClickListener {
            anular = true
            count = holder.cant.text.toString().toInt()
            count += 1
            holder.cant.text = count.toString()
            holder.delete.visibility = View.GONE
            holder.remove.visibility = View.VISIBLE
            holder.view_cant.text = count.toString()

            Handler(Looper.getMainLooper()).postDelayed({
                if (count > 1) {

                    holder.contenedor.visibility = View.GONE
                    holder.view_cant.visibility = View.VISIBLE

                   for (lista in viewmodel.cart.value!!){
                       if(lista.idprod == item.idprod){
                           Log.d("tag", " Sumar - actualizar")
                           //actualizardatos(item)
                       }else{
                           Log.d("tag", " Sumar - crear")
                           crear(item)
                       }
                   }




                    anular = false
                }


            }, 3000)
        }
        holder.remove.setOnClickListener {



            count = holder.cant.text.toString().toInt()
            count -= 1
            actualizardatos(item)
            holder.view_cant.text = count.toString()
            if(count == 1){
                holder.delete.visibility = View.VISIBLE
                holder.remove.visibility = View.GONE

            }
            holder.cant.text = count.toString()



            Handler(Looper.getMainLooper()).postDelayed({

                    holder.contenedor.visibility = View.GONE
                    holder.view_cant.visibility = View.VISIBLE
                    holder.view_cant.text = count.toString()
                    anular = false



            }, 3000)
        }


        holder.delete.setOnClickListener {

            deleteItem(item)

            anular = true
            holder.contenedor.visibility = View.GONE
            holder.btnadd.visibility = View.VISIBLE
            anular = false
        }

        holder.view_cant.setOnClickListener {
            holder.view_cant.visibility = View.GONE
            holder.contenedor.visibility = View.VISIBLE
            count = holder.view_cant.text.toString().toInt()

            Handler(Looper.getMainLooper()).postDelayed({
                if(count > 1 && !anular){
                    holder.contenedor.visibility = View.GONE
                    holder.view_cant.visibility = View.VISIBLE
                    anular = false
                }


            }, 3000)
        }



        //holder.render(item)

    }

    private fun deleteItem(item: Productos) {
        val id = item.idprod

        val lista = ArrayList<Productos>()

        for (item in viewmodel.cart.value!!){
            if(item.idprod != id){
                lista.add(Productos(item.idprod,item.idcate,item.nombre,item.cantidad,item.precio))
            }

        }
        anular = false
        viewmodel.cart.value = lista
    }


    private fun crear(item: Productos) {
        Log.d("tag","SOY CREAR")


         if(viewmodel.cart.value == null){
                 Log.d("tag","entre null")
                 val list = mutableListOf<Productos>()
                 list.add(Productos(item.idprod,"",item.nombre,count.toString(),item.precio))
                 viewmodel.cart.value = list
                 count = 1
             }else{
                 Log.d("tag","entre al cart no null")
                 val list = mutableListOf<Productos>()
                 list.add(Productos(item.idprod,"",item.nombre,count.toString(),item.precio))
                 viewmodel.cart.value = viewmodel.cart.value!! + list
                 count = 1
             }
    }

    private fun actualizardatos(item: Productos) {
        Log.d("tag","SOY ACTUALIZAR")
        val id = item.idprod

        val lista = ArrayList<Productos>()

        if(viewmodel.cart.value.isNullOrEmpty()){
            val list = mutableListOf<Productos>()
            list.add(Productos(item.idprod,"",item.nombre,count.toString(),item.precio))
            viewmodel.cart.value = list
        }else{
            for (items in viewmodel.cart.value!!){
                if(items.idprod == id){
                    lista.add(Productos(items.idprod,items.idcate,items.nombre,count.toString(),items.precio))
                }else{
                    lista.add(Productos(items.idprod,items.idcate,items.nombre,items.cantidad,items.precio))
                }

                viewmodel.cart.value = lista

            }
        }
    }

    override fun getItemCount(): Int = lista.size



}

class ProductosViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    val btnadd = view.findViewById<Button>(R.id.btn_add)
    val contenedor = view.findViewById<LinearLayout>(R.id.contenedor_add)
    val sumar = view.findViewById<ImageView>(R.id.btn_addmas)
    val delete = view.findViewById<ImageView>(R.id.id_delete)
    val cant = view.findViewById<TextView>(R.id.tv_cant)
    val view_cant = view.findViewById<TextView>(R.id.textview_cant)
    val remove = view.findViewById<ImageView>(R.id.btn_remove)
    val nombre = view.findViewById<TextView>(R.id.tv_nombre)
    fun render (item : Productos){
      //titulo.text = item.nombre


    }

}
