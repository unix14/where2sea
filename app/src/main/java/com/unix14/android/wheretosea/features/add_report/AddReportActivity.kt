package com.unix14.android.wheretosea.features.add_report

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.common.Constants
import com.unix14.android.wheretosea.features.add_report.add_report.AddReportFragment

class AddReportActivity : AppCompatActivity(), AddReportFragment.AddReportFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_report_activity)

        initUi()
    }

    private fun initUi() {
        showFragment(AddReportFragment.newInstance(), Constants.ADD_REPORT_FRAGMENT)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.popup_show, 0, 0, R.anim.popup_hide)
            .replace(R.id.mainActContainer, fragment, tag)
            .commit()
    }

    override fun onWantToDive() {
        Toast.makeText(this, "Clicked on Diving", Toast.LENGTH_LONG).show()
    }

    override fun onWantToSurf() {
        Toast.makeText(this, "Clicked on Surfing", Toast.LENGTH_LONG).show()
    }

}
