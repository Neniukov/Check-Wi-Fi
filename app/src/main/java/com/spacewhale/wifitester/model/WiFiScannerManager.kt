package com.spacewhale.wifitester.model

import android.content.Context
import com.github.pwittchen.reactivewifi.ReactiveWifi
import com.github.pwittchen.reactivewifi.WifiState
import com.spacewhale.wifitester.core.data.WifiZone
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WiFiScannerManager : WiFiScanner {

    override fun startWifiScanner(context: Context): Observable<WifiZone> {
        val countLevelsSignal = WifiZone::class.nestedClasses.size
        return Observable.create { emitter ->
            ReactiveWifi.observeWifiSignalLevel(context, countLevelsSignal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ level ->
                    val wifiZone = definitionZone(level)
                    emitter.onNext(wifiZone)
                }, {
                    emitter.onError(it)
                })
        }
    }

    override fun checkWifiConnection(context: Context): Observable<WifiState> {
        return Observable.create { emitter ->
            ReactiveWifi.observeWifiStateChange(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wifiState ->
                    emitter.onNext(wifiState)
                }, {
                    emitter.onError(it)
                })
        }
    }

    private fun definitionZone(level: Int?): WifiZone {
        return when (level) {
            WifiZone.INDEFINITE().levelSignal -> WifiZone.INDEFINITE()
            WifiZone.LOW().levelSignal -> WifiZone.LOW()
            WifiZone.MODERATE().levelSignal -> WifiZone.MODERATE()
            WifiZone.HIGH().levelSignal -> WifiZone.HIGH()
            else -> {
                WifiZone.INDEFINITE()
            }
        }
    }
}