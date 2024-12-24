package com.example.boje2

data class Order(
    val userId: String,
    val serviceName: String,
    val serviceDescription: String,
    val servicePrice: String,
    val orderDate: Long = System.currentTimeMillis()
)
