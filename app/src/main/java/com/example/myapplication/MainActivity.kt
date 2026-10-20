package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensorPasos: Sensor? = null
    private lateinit var tvContador: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        tvContador = findViewById(R.id.tvContador)
        recyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView()

        // Permisos y Sensor
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 100)
        } else {
            configurarSensor()
        }
    }

    private fun setupRecyclerView() {
        val activityList = listOf(
            ActivityItem("Pasos Diarios", "Meta: 10,000 pasos", "747", android.R.drawable.ic_menu_directions, android.R.color.holo_purple),
            ActivityItem("Natación", "Meta: 10,000 pasos", "747", android.R.drawable.ic_menu_info_details, android.R.color.holo_blue_dark),
            ActivityItem("Rutina de Fuerza", "Meta: 10,000 pasos", "747", android.R.drawable.ic_menu_sort_by_size, android.R.color.holo_red_dark),
            ActivityItem("Sesión de box", "Meta: 10,000 pasos", "747", android.R.drawable.ic_menu_sort_by_size, android.R.color.holo_red_light),
            ActivityItem("Desayuno", "Meta: 10,000 pasos", "747", android.R.drawable.ic_menu_gallery, android.R.color.holo_green_dark)
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ActivityAdapter(activityList, this)
    }

    private fun configurarSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorPasos = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (sensorPasos == null) {
            tvContador.text = "Error sensor"
        }
    }

    override fun onResume() {
        super.onResume()
        sensorPasos?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val steps = event.values[0].toInt()
            tvContador.text = steps.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Perfil")?.apply {
            setIcon(android.R.drawable.ic_menu_myplaces)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 1) {
            startActivity(Intent(this, ProfileActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}