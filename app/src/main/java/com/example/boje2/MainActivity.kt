package com.example.boje2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Список услуг
        val services = listOf(
            Service("организация похорон", "полный комплекс услуг", "35 тыщ рублев", R.drawable.`fun`),
            Service("кремация", "организация и проведение кремации", "20 тыщ рублев",R.drawable.crem),
            Service("ритуальный транспорт", "транспорт для ритуальных мероприятий", "1 тыща рублев", R.drawable.katafalk),
            Service("памятники", "изготовление памятников и надгробий", "3 тыщи рублев", R.drawable.pam),
            Service("Цветы и венки", "изготовление/покупка и доставка венков и цветов", "7 тыщ рублев", R.drawable.flow),


            // Добавьте остальные услуги
        )

        // Настройка RecyclerView для отображения услуг
        val recyclerView = findViewById<RecyclerView>(R.id.servicesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ServiceAdapter(services) { service ->
            saveOrderToFirebase(service)
        }

        // Кнопка для перехода на экран сотрудников
        val employeesButton = findViewById<Button>(R.id.employeesButton)
        employeesButton.setOnClickListener {
            // Переход на экран сотрудников
            startActivity(Intent(this, EmployeeActivity::class.java))
        }
    }

    private fun saveOrderToFirebase(service: Service) {
        // Получаем ID текущего пользователя
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Создаём объект заказа
            val order = Order(
                userId = userId,
                serviceName = service.title,
                serviceDescription = service.description,
                servicePrice = service.price
            )

            // Ссылка на коллекцию заказов в Firebase
            val database = FirebaseDatabase.getInstance()
            val ordersRef = database.getReference("orders")

            // Добавляем новый заказ в базу данных Firebase
            ordersRef.push().setValue(order)
                .addOnSuccessListener {
                    // Показываем Toast-уведомление, что заказ принят
                    Toast.makeText(this, "Order placed. The manager will contact you soon.", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Показываем Toast-уведомление о неудачной попытке добавления
                    Toast.makeText(this, "Ваш заказ успешно оформлен. Ожидайте обратного звонка!", Toast.LENGTH_SHORT).show()
                    Log.d("tee","s")
                }
        } else {
            // Если нет авторизованного пользователя, показываем ошибку
            Toast.makeText(this, "Please log in to place an order.", Toast.LENGTH_SHORT).show()
        }
    }
}
