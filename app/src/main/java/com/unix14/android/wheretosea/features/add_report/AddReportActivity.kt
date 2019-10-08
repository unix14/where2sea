package com.unix14.android.wheretosea.features.add_report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unix14.android.wheretosea.R
import com.unix14.android.wheretosea.common.Constants
import com.unix14.android.wheretosea.features.add_report.ui.addreport.AddReportFragment

class AddReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_report_activity)

        initUi()
    }

    private fun initUi() {
        showFragment(AddReportFragment(), Constants.ADD_REPORT_FRAGMENT)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.popup_show, 0, 0, R.anim.popup_hide)
            .replace(R.id.mainActContainer, fragment, tag)
            .commit()
    }

}
