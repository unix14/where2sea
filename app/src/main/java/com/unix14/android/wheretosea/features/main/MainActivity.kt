package com.unix14.android.wheretosea.features.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.models.Forecast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterListener {

    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        initUi()
        initObservers()

        viewModel.fetchForecast(32.048157f,34.7467756f)
    }

    private fun initUi() {
        mainActRecyclerView.layoutManager = LinearLayoutManager(this)
        mainActRecyclerView.adapter = MainAdapter(this)


    }

    private fun initObservers() {
        viewModel.forecastResponse.observe(this, Observer {
            forecast -> handleForecast(forecast)
        })
        viewModel.progressData.observe(this, Observer {
            isLoading -> handleProgress(isLoading)
        })
        viewModel.errorEvent.observe(this, Observer {
            errorEvent -> handleErrors(errorEvent)
        })
    }

    private fun handleErrors(errorEvent: MainViewModel.ErrorEvent?) {
        errorEvent?.let {
            when(it){
                is MainViewModel.ErrorEvent.Error -> {
                    if(it.errorMsg.isNotEmpty()){
                        Toast.makeText(this,it.errorMsg,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun handleProgress(isLoading: Boolean?) {
        isLoading?.let {
            if(isLoading){
                mainActPb.visibility = View.VISIBLE
            }else{
                mainActPb.visibility = View.GONE
            }
        }
    }

    private fun handleForecast(forecast: Forecast?) {
        forecast?.let{
//            mainActRecyclerView


            val entries = ArrayList<Entry>()
            var i = 0
            for (i in 0 until it.hours.size) {
                // turn your data into Entry objects
                entries.add(Entry(i.toFloat(), it.hours[i].seaLevel[0].value))
            }

            val dataSet = LineDataSet(entries, "Sea Level")

            val lineData = LineData(dataSet)
            mainActChart.data = lineData
            mainActChart.invalidate() // refresh
        }
    }

//    override fun onItemClick(forecast: Forecast) {
//        Toast.makeText(this,forecast.toString(),Toast.LENGTH_LONG).show()
//    }
}
