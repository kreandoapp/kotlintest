package com.example.firebasekotlin.locales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasekotlin.R
import com.example.firebasekotlin.adapter.AdapterLocales
import com.example.firebasekotlin.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.activity_locales.*

class LocalesActivity : AppCompatActivity() {

    private val viewmodel : FirebaseViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locales)

        initRecyclerview()
    }

    private fun initRecyclerview() {
        rv_recyclerview.layoutManager = LinearLayoutManager(this)
        viewmodel.listLocales.observe(this){
            rv_recyclerview.adapter = AdapterLocales(it)

        }

    }
}