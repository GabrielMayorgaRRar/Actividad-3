package com.example.myapplication

import java.io.Serializable

data class ActivityItem(
    val title: String,
    val subtitle: String,
    val trailingText: String,
    val iconResId: Int,
    val colorResId: Int
) : Serializable