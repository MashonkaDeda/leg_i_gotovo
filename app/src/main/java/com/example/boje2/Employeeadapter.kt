package com.example.boje2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class EmployeeAdapter(private val employees: List<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.nameTextView.text = employee.name
        holder.positionTextView.text = employee.position
        holder.imageView.setImageResource(employee.imageResource)
    }

    override fun getItemCount(): Int = employees.size

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.employeeName)
        val positionTextView: TextView = itemView.findViewById(R.id.employeePosition)
        val imageView: ImageView = itemView.findViewById(R.id.employeeImage)
    }
}
