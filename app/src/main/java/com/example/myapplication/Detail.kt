package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvResumen = findViewById<TextView>(R.id.tvResumen)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        val tituloActividad = intent.getStringExtra("ACTIVITY_TITLE") ?: "Detalles de la actividad"
        tvResumen.text = tituloActividad

        supportActionBar?.title = tituloActividad

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}