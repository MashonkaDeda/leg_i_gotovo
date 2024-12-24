package com.example.boje2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.content.Context


class ServiceAdapter(
    private val services: List<Service>,           // Список услуг
    private val onOrderClick: (Service) -> Unit    // Обработчик нажатия на кнопку "Order"
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    // Внутренний класс для ViewHolder
    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val price: TextView = itemView.findViewById(R.id.priceTextView)
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val orderButton: Button = itemView.findViewById(R.id.orderButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.title.text = service.title
        holder.description.text = service.description
        holder.price.text = service.price
        holder.image.setImageResource(service.imageResource)

        // Обработчик нажатия на кнопку "Order"
        holder.orderButton.setOnClickListener {
            // Вызываем метод для сохранения заказа
            saveOrderToFirebase(service,holder.itemView.context)
        }
    }

    override fun getItemCount(): Int = services.size

    // Метод для сохранения заказа в Firebase
    private fun saveOrderToFirebase(service: Service, context: Context) {
        // Получаем ID текущего пользователя
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Создаем объект заказа
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
                    Toast.makeText(context, "Order placed. The manager will contact you soon.", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    // Показываем Toast-уведомление о неудачной попытке добавления
                    Toast.makeText(context, "Ваш заказ успешно оформлен. Ожидайте обратного звонка!", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Если нет авторизованного пользователя, показываем ошибку
            Toast.makeText(context, "Please log in to place an order.", Toast.LENGTH_SHORT).show()
        }
    }

}





