package com.spacewhale.wifitester.feature.scannerWIFI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.spacewhale.wifitester.R
import com.spacewhale.wifitester.core.data.WifiZone
import com.spacewhale.wifitester.utils.*
import kotlinx.android.synthetic.main.fragment_content_scan.*

const val REQUEST_ID_PERMISSIONS = 1221
private const val MOVE_DURATION = 3000L

class ScanWiFiFragment : BaseMvRxFragment() {

    private val viewModel: ScanWiFiViewModel by fragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        onWifiSignalListener()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ID_PERMISSIONS -> {
                if (isPermissionResultSuccess(requestCode, grantResults)) {
                    viewModel.checkWifi(requireContext())
                    viewModel.startObservationOfSignal(requireContext())
                }
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) {
            val wifiZone = it.zone.invoke()
            if (wifiZone != null) {
                wifiZone.levelSignal.let { level ->
                    progressSignal.speedTo(level.toFloat(), MOVE_DURATION)
                }
                if (wifiZone.levelSignal > WifiZone.LOW().levelSignal) {
                    clickButtonView.setOnClickListener {
                        Toast.makeText(requireContext(), R.string.ipsum_lorem, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    clickButtonView.setOnClickListener { Unit }
                }
            } else {
                clickButtonView.setOnClickListener { Unit }
                progressSignal.speedTo(WifiZone.INDEFINITE().levelSignal.toFloat(), MOVE_DURATION)
            }
        }
    }

    private fun onWifiSignalListener() {
        progressSignal.setOnSpeedChangeListener { gauge, _, _ ->
            val speed = gauge?.currentIntSpeed
            speed?.let { progressSignal.speedometerColor = ColorHelper.getColor(speed) }
        }
    }

    private fun checkPermission() {
        if (!isLocationPermissionGranted(requireContext())) {
            viewModel.checkWifi(requireContext())
            viewModel.startObservationOfSignal(requireContext())
        } else {
            requestPermissions(arrayOf(locationCoarsePermission, locationFinePermission), REQUEST_ID_PERMISSIONS)
        }
    }
}