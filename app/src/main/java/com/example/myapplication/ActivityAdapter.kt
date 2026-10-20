package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private val activityList: List<ActivityItem>,
    private val context: Context
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    private val prefs: SharedPreferences = context.getSharedPreferences("ActivityPrefs", Context.MODE_PRIVATE)

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
        val tvTrailing: TextView = view.findViewById(R.id.tvTrailing)
        val cbCompleted: CheckBox = view.findViewById(R.id.cbCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val item = activityList[position]

        holder.tvTitle.text = item.title
        holder.tvTrailing.text = item.trailingText
        holder.ivIcon.setImageResource(item.iconResId)
        holder.ivIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, item.colorResId))

        val isCompleted = prefs.getBoolean(item.title, false)
        holder.cbCompleted.isChecked = isCompleted
        actualizarVista(holder, item.subtitle, isCompleted)

        holder.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(item.title, isChecked).apply()
            actualizarVista(holder, item.subtitle, isChecked)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, Detail::class.java)
            intent.putExtra("ACTIVITY_TITLE", item.title)
            context.startActivity(intent)
        }
    }

    private fun actualizarVista(holder: ActivityViewHolder, subtitle: String, isCompleted: Boolean) {
        if (isCompleted) {
            holder.tvSubtitle.text = "$subtitle • Completado"
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C8E6C9"))
        } else {
            holder.tvSubtitle.text = "$subtitle • Pendiente"
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int = activityList.size
}