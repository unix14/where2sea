package com.unix14.android.wheretosea.features.add_report.add_report

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.unix14.android.wheretosea.R
import kotlinx.android.synthetic.main.add_report_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddReportFragment : Fragment() {

    interface AddReportFragmentListener {
        fun onWantToDive()
        fun onWantToSurf()
    }

    companion object {
        fun newInstance() = AddReportFragment()
    }

    private var listener: AddReportFragmentListener? = null
    val viewModel by viewModel<AddReportViewModel>()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.add_report_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()
        initUi()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AddReportFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement AddReportFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initClicks() {
        setOnTouchTint(addReportFragSurfing)
        setOnTouchTint(addReportFragDiving)

        addReportFragDiving.setOnClickListener {
            listener?.onWantToDive()
        }
        addReportFragSurfing.setOnClickListener {
            listener?.onWantToSurf()
        }
    }

    private fun setOnTouchTint(imageView: ImageView) {
        imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val view = v as ImageView
                    //overlay is black with transparency of 0x77 (119)
                    view.drawable.setColorFilter(0x50000000, PorterDuff.Mode.SRC_ATOP)
                    view.invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val view = v as ImageView
                    //clear the overlay
                    view.drawable.clearColorFilter()
                    view.invalidate()
                }
            }

            false
        }
    }

    private fun initUi() {

    }
}
