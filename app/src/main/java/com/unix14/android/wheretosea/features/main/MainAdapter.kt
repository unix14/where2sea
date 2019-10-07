package com.unix14.android.wheretosea.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.models.Forecast

class MainAdapter(val listener: MainAdapterListener) :
    ListAdapter<Forecast, MainAdapter.LocalForecast>(ForecastListDiffCallback()) {

    interface MainAdapterListener{
//        fun onItemClick(forecast: Forecast)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocalForecast(LayoutInflater.from(parent.context).inflate(R.layout.local_forecast_item, parent, false))


    override fun onBindViewHolder(holder: LocalForecast, position: Int) {
        var forecast = getItem(position)

        holder.itemView.setOnClickListener {
            //todo:: enable on click callback
//            listener.onItemClick(forecast)
        }

    }

    class LocalForecast(view: View) : RecyclerView.ViewHolder(view) {

        fun initCard(forecast: Forecast) {

            //TODO:: Do something with forecast

        }
    }

    class ForecastListDiffCallback : DiffUtil.ItemCallback<Forecast>() {

        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem.meta.lat == newItem.meta.lat && oldItem.meta.lng == newItem.meta.lng
        }

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }
    }
}