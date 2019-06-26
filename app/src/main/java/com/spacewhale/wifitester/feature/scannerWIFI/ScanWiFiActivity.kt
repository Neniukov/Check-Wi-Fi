package com.spacewhale.wifitester.feature.scannerWIFI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spacewhale.wifitester.R
import kotlinx.android.synthetic.main.layout_toolbar.*

private const val TAG_SCAN_WIFI = "scannerWIFI"

class ScanWiFiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_wifi)
        initToolbar()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameScanner, ScanWiFiFragment(), TAG_SCAN_WIFI)
            addToBackStack(null)
            commit()
        }
    }

    private fun initToolbar() {
        toolbar.apply {
            navigationIcon = getDrawable(R.drawable.ic_close)
            title = getString(R.string.cancel)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}