package com.example.boje2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.content.Intent

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // Список сотрудников
        val employees = listOf(
            Employee("Мария Раздымаха", "Владелец/разработчик", R.drawable.masha),
            Employee("Валерий Землянский", "Организатор смерти", R.drawable.valery),
            Employee("Сергей Молчанов", "Водила катафалко", R.drawable.seruy),
            Employee("Станислав Токарь", "Гробовщик и еще кремирует", R.drawable.sts),
            Employee("Иван Емельянов", "Могильщик и цветочки положит", R.drawable.ivan)
        )

        // Настройка RecyclerView для отображения сотрудников
        val recyclerView = findViewById<RecyclerView>(R.id.employeesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EmployeeAdapter(employees)

        // Обработчик для кнопки возврата на основную активность
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            // Возвращаемся на MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
