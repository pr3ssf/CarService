package com.example.carservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Car(
    val title: String,
    val brand: String,
    val price: Int,
    val fuelType: String
)

class CarAdapter(
    private val cars: List<Car>
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCarImage = itemView.findViewById<ImageView>(R.id.ivCarImage)
        val tvCarTitle = itemView.findViewById<TextView>(R.id.tvCarTitle)
        val tvCarBrand = itemView.findViewById<TextView>(R.id.tvCarBrand)
        val tvCarPrice = itemView.findViewById<TextView>(R.id.tvCarPrice)
        val tvPerDay = itemView.findViewById<TextView>(R.id.tvPerDay)
        val tvFuelType = itemView.findViewById<TextView>(R.id.tvFuelType)
        val btnBook = itemView.findViewById<Button>(R.id.btnBook)
        val btnDetails = itemView.findViewById<Button>(R.id.btnDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.tvCarTitle.text = car.title
        holder.tvCarBrand.text = car.brand
        holder.tvCarPrice.text = "${car.price}Р"
        // holder.ivCarImage.setImageResource(...) // если есть ресурс
        holder.tvFuelType.text = " | ${car.fuelType}"

        // Клики по кнопкам
        holder.btnBook.setOnClickListener {
            // TODO: действие по нажатию "Забронировать"
        }
        holder.btnDetails.setOnClickListener {
            // TODO: действие по нажатию "Детали"
        }
    }

    override fun getItemCount(): Int = cars.size
}
