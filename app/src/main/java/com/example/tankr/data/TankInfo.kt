package com.example.tankr.data

data class TankInfo(
    val flowLpm: Double,
    val lastReadingAt: String,
    val liters: Int,
    val maxLiters: Int,
    val name: String,
    val percentage: Int,
    val pump: Pump,
    val secondsSinceLastReading: Int,
    val status: String,
    val tankId: String
)