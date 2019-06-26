package com.spacewhale.wifitester.model

import android.content.Context
import com.github.pwittchen.reactivewifi.WifiState
import com.spacewhale.wifitester.core.data.WifiZone
import io.reactivex.Observable

interface WiFiScanner {
    fun startWifiScanner(context: Context): Observable<WifiZone>
    fun checkWifiConnection(context: Context): Observable<WifiState>
}