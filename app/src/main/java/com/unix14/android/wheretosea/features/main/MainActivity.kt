package com.unix14.android.wheretosea.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.features.add_report.AddReportActivity
import com.unix14.android.wheretosea.models.Forecast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), MainAdapter.MainAdapterListener,
    ChipNavigationBar.OnItemSelectedListener {

    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initNavBar()
//        initUi()
        initObservers()

        viewModel.fetchForecast(32.048157f,34.7467756f)
    }

    private fun initNavBar() {
        mainActNavBar.setItemSelected(R.id.home)
        mainActNavBar.setOnItemSelectedListener(this)
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

    private fun openAddReportActivity(){
        val intent = Intent(this,AddReportActivity::class.java)
        startActivity(intent)
    }

    private fun handleForecast(forecast: Forecast?) {
        forecast?.let{
//            mainActRecyclerView


            val entries = ArrayList<Entry>()
            for (i in 0 until it.hours.size) {
                // turn your data into Entry objects
                entries.add(Entry(i.toFloat(), it.hours[i].seaLevel[0].value))
            }

            val dataSet = LineDataSet(entries, getString(R.string.common_string_sea_level))

            val lineData = LineData(dataSet)
            mainActChart.data = lineData
            mainActChart.invalidate() // refresh
        }
    }

//    override fun onItemClick(forecast: Forecast) {
//        Toast.makeText(this,forecast.toString(),Toast.LENGTH_LONG).show()
//    }

    override fun onItemSelected(id: Int) {
        when(id){
            R.id.home -> {
                Toast.makeText(this,"Clicked on Home",Toast.LENGTH_LONG).show()
            }
            R.id.pulse -> {
                Toast.makeText(this,"Clicked on Pulse",Toast.LENGTH_LONG).show()
                openAddReportActivity()
            }
            R.id.favorites -> {
                Toast.makeText(this,"Clicked on Favorites",Toast.LENGTH_LONG).show()
            }
            R.id.settings -> {
                Toast.makeText(this,"Clicked on Settings",Toast.LENGTH_LONG).show()
            }
        }
    }
}
