package com.spacewhale.wifitester.feature.scannerWIFI

import android.content.Context
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.github.pwittchen.reactivewifi.WifiState
import com.spacewhale.wifitester.core.data.WifiSignal
import com.spacewhale.wifitester.model.WiFiScanner
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class ScanWiFiViewModel(wifiSignal: WifiSignal, private val wifiScanner: WiFiScanner) :
    BaseMvRxViewModel<WifiSignal>(wifiSignal, debugMode = true) {

    private lateinit var disposable: Disposable

    companion object : MvRxViewModelFactory<ScanWiFiViewModel, WifiSignal> {
        override fun create(viewModelContext: ViewModelContext, state: WifiSignal): ScanWiFiViewModel? {
            val wifiScanner: WiFiScanner by viewModelContext.activity.inject()
            return ScanWiFiViewModel(state, wifiScanner)
        }
    }

    override fun onCleared() {
        if (::disposable.isInitialized && !disposable.isDisposed) {
            disposable.dispose()
        }
        super.onCleared()
    }

    fun checkWifi(context: Context) {
        disposable = wifiScanner.checkWifiConnection(context)
            .subscribeOn(Schedulers.io())
            .execute { wifiState ->
                val stateDescription = wifiState.invoke()?.description
                stateDescription?.let { state ->
                    if (state == WifiState.DISABLED.description) {
                        copy(zone = Uninitialized)
                    } else {
                        copy(zone = zone)
                    }
                } ?: copy(zone = zone)
            }
    }

    fun startObservationOfSignal(context: Context) {
        disposable = wifiScanner.startWifiScanner(context)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(zone = it)
            }
    }
}